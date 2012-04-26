/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package npanday.plugin.libraryimporter.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import npanday.model.library.imports.ReferenceMapping;
import npanday.plugin.libraryimporter.AssemblyInfo;
import npanday.plugin.libraryimporter.LibImporterPathUtils;
import npanday.plugin.libraryimporter.ManifestInfoParser;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="me@lcorneliussen.de">Lars Corneliussen, Faktum Software</a>
 */
public class NugetPackageLibrary
{
    private final NugetPackage nuget;

    private final File lib;

    private File mavenProjectsCacheDirectory;

    private String name;

    private AssemblyInfo assemblyInfo;

    private List<NugetPackageLibrary> dependencies;

    public NugetPackageLibrary( NugetPackage nuget, File lib, File mavenProjectsCacheDirectory )
    {
        this.nuget = nuget;
        this.lib = lib;
        this.mavenProjectsCacheDirectory = mavenProjectsCacheDirectory;
        this.name = LibImporterPathUtils.removeFileExtension( lib.getName() );
    }

    public String getName()
    {
        return name;
    }

    public String getMavenVersion()
    {
        return nuget.getMavenVersion();
    }

    public String getMavenPackaging() throws MojoExecutionException
    {
        if ( LibImporterPathUtils.isDll( lib ) )
        {
            return "dotnet-library";
        }
        else if ( LibImporterPathUtils.isExe( lib ) )
        {
            return "dotnet-executable";
        }

        throw new MojoExecutionException(
            "NPANDAY-145-001: Could not determine maven packaging for of " + nuget.getName() + " / " + name
        );
    }

    public String getMavenGroupId()
    {
        return nuget.getNuspec().getId();
    }

    public String getMavenArtifactId()
    {
        return name;
    }

    public File getFile()
    {
        return lib;
    }

    public NugetPackage getNugetPackage()
    {
        return nuget;
    }

    public AssemblyInfo getAssemblyInfo()
    {
        if ( assemblyInfo == null )
        {
            assemblyInfo = ManifestInfoParser.parse( getManifestInfoFile(), name );
        }
        return assemblyInfo;
    }

    public Collection<NugetPackageLibrary> resolveDependenciesFrom(
        List<NugetPackageLibrary> imports ) throws MojoExecutionException
    {
        Iterable<AssemblyInfo> references = getAssemblyInfo().getReferences();

        dependencies = Lists.newArrayList();

        for ( final AssemblyInfo ref : references )
        {
            if ( isExcluded( ref ) )
            {
                continue;
            }

            List<NugetPackageLibrary> matchingStrongName = Lists.newArrayList();
            List<NugetPackageLibrary> matchingStrongNameAndPackage = Lists.newArrayList();
            List<NugetPackageLibrary> matchingName = Lists.newArrayList();
            List<NugetPackageLibrary> matchingNameAndPackage = Lists.newArrayList();
            for ( NugetPackageLibrary lib : imports )
            {
                boolean inScope = nuget.isDependentOn( lib.getNugetPackage() ) || nuget == lib.getNugetPackage();

                if ( lib.getAssemblyInfo().getStrongName().equals( ref.getStrongName() ) )
                {
                    matchingStrongName.add( lib );
                    if ( inScope )
                    {
                        matchingStrongNameAndPackage.add( lib );
                    }
                }
                if ( lib.name.equals( ref.getName() ) )
                {
                    matchingName.add( lib );
                    if ( inScope )
                    {
                        matchingNameAndPackage.add( lib );
                    }
                }
            }

            if ( matchingStrongNameAndPackage.size() == 0 )
            {
                String help = "";
                if ( matchingNameAndPackage.size() > 0 )
                {
                    help = ", but found matches by name and package: " + matchingName;
                }
                if ( matchingStrongName.size() > 0 )
                {
                    help = ", but found exact matches in different package(s): " + matchingStrongName;
                }
                if ( matchingName.size() > 0 )
                {
                    help = ", but found matches by name only: " + matchingName;
                }

                if ( !Strings.isNullOrEmpty( help ) )
                {
                    help += ". Please use the config to tweak reference resolving.";
                }

                throw new MojoExecutionException(
                    "NPANDAY-145-002: Could not find exact match for reference from " + nuget.getName() + " / " + name
                        + " to '" + ref.getStrongName() + "'" + help
                );
            }

            if ( matchingStrongNameAndPackage.size() > 1 )
            {
                throw new MojoExecutionException(
                    "NPANDAY-145-003: found multiple matches for " + nuget.getName() + " / " + name + " to '"
                        + ref.getStrongName() + "': " + matchingStrongNameAndPackage
                        + ". Please use the config to tweak reference resolving."
                );
            }

            dependencies.add( matchingStrongNameAndPackage.get( 0 ) );
        }

        return dependencies;
    }

    private boolean isExcluded( AssemblyInfo ref )
    {
        if ( ref.getStrongName().endsWith( /* .NET 2.0 BCL, mscorlib, ... */ "b77a5c561934e089" ) )
        {
            return true;
        }

        if ( ref.getStrongName().endsWith( /* .NET 4.0 BCL */ "b03f5f7f11d50a3a" ) )
        {
            return true;
        }

        if ( ref.getStrongName().endsWith( /* .NET Web Team */ "bf3856ad364e35" ) )
        {
            return true;
        }

        if ( ref.getName().startsWith( "System." ) )
        {
            return true;
        }

        ReferenceMapping referenceMapping = nuget.tryFindReferenceMappingFor( ref );
        if (referenceMapping != null && referenceMapping.isIgnore())
        {
            return true;
        }

        return false;
    }

    public File getManifestInfoFile()
    {
        return new File( lib.getParentFile(), "ManifestInfo.xml" );
    }

    @Override
    public String toString()
    {
        String lib = getManifestInfoFile().exists() ? getAssemblyInfo().getStrongName() : name;
        return "[package: " + nuget + ", lib: " + lib + "]";
    }

    public List<NugetPackageLibrary> getDependencies()
    {
        Preconditions.checkArgument(
            dependencies != null, "NPANDAY-145-004: Dependencies for " + this + " have not been resolved (yet)!"
        );
        return dependencies;
    }

    public File getMavenPomFile() throws MojoExecutionException
    {
        return new File( getMavenProjectFolder(), "pom.xml" );
    }

    public File getMavenArtifactFile() throws MojoExecutionException
    {
        return new File( getMavenProjectFolder(), getFile().getName() );
    }

    public File getMavenProjectFolder() throws MojoExecutionException
    {
        String folderName = getMavenGroupId() + "_" + getMavenArtifactId() + "_" + getMavenPackaging() + "_" + getMavenVersion();
        return new File( mavenProjectsCacheDirectory, folderName);
    }
}