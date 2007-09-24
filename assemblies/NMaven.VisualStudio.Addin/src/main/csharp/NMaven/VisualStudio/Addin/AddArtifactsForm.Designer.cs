namespace NMaven.VisualStudio.Addin
{
    partial class AddArtifactsForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AddArtifactsForm));
            this.addArtifact = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.remoteTabPage = new System.Windows.Forms.TabPage();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.localTabPage = new System.Windows.Forms.TabPage();
            this.localListView = new System.Windows.Forms.ListView();
            this.ArtifactNameHeader = new System.Windows.Forms.ColumnHeader();
            this.versionHeader = new System.Windows.Forms.ColumnHeader();
            this.artifactTabControl = new System.Windows.Forms.TabControl();
            this.remoteTabPage.SuspendLayout();
            this.localTabPage.SuspendLayout();
            this.artifactTabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // addArtifact
            // 
            this.addArtifact.Location = new System.Drawing.Point(447, 374);
            this.addArtifact.Name = "addArtifact";
            this.addArtifact.Size = new System.Drawing.Size(75, 23);
            this.addArtifact.TabIndex = 1;
            this.addArtifact.Text = "Add";
            this.addArtifact.UseVisualStyleBackColor = true;
            this.addArtifact.Click += new System.EventHandler(this.addArtifact_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(546, 374);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 2;
            this.button2.Text = "Cancel";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "unknown.gif");
            // 
            // remoteTabPage
            // 
            this.remoteTabPage.Controls.Add(this.treeView1);
            this.remoteTabPage.Location = new System.Drawing.Point(4, 25);
            this.remoteTabPage.Name = "remoteTabPage";
            this.remoteTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.remoteTabPage.Size = new System.Drawing.Size(628, 309);
            this.remoteTabPage.TabIndex = 1;
            this.remoteTabPage.Text = "Remote";
            this.remoteTabPage.UseVisualStyleBackColor = true;
            // 
            // treeView1
            // 
            this.treeView1.Location = new System.Drawing.Point(23, 18);
            this.treeView1.Name = "treeView1";
            this.treeView1.Size = new System.Drawing.Size(582, 272);
            this.treeView1.TabIndex = 0;
            // 
            // localTabPage
            // 
            this.localTabPage.Controls.Add(this.localListView);
            this.localTabPage.Location = new System.Drawing.Point(4, 25);
            this.localTabPage.Name = "localTabPage";
            this.localTabPage.Padding = new System.Windows.Forms.Padding(3);
            this.localTabPage.Size = new System.Drawing.Size(628, 309);
            this.localTabPage.TabIndex = 0;
            this.localTabPage.Text = "Local";
            this.localTabPage.UseVisualStyleBackColor = true;

            // 
            // localListView
            // 
            this.localListView.BackgroundImageTiled = true;
            this.localListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.ArtifactNameHeader,
            this.versionHeader});
            this.localListView.Location = new System.Drawing.Point(22, 19);
            this.localListView.Name = "localListView";
            this.localListView.Size = new System.Drawing.Size(583, 270);
            this.localListView.Sorting = System.Windows.Forms.SortOrder.Ascending;
            this.localListView.TabIndex = 0;
            this.localListView.UseCompatibleStateImageBehavior = false;

            // 
            // ArtifactNameHeader
            // 
            this.ArtifactNameHeader.Text = "Artifact Name";
            this.ArtifactNameHeader.Width = 240;
            // 
            // versionHeader
            // 
            this.versionHeader.Text = "Version";
            this.versionHeader.Width = 120;
            // 
            // artifactTabControl
            // 
            this.artifactTabControl.Controls.Add(this.localTabPage);
            this.artifactTabControl.Controls.Add(this.remoteTabPage);
            this.artifactTabControl.Location = new System.Drawing.Point(12, 12);
            this.artifactTabControl.Name = "artifactTabControl";
            this.artifactTabControl.SelectedIndex = 0;
            this.artifactTabControl.Size = new System.Drawing.Size(636, 338);
            this.artifactTabControl.TabIndex = 0;
            // 
            // AddArtifactsForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(677, 413);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.addArtifact);
            this.Controls.Add(this.artifactTabControl);
            this.Name = "AddArtifactsForm";
            this.Text = "Add Maven Artifact";
            this.remoteTabPage.ResumeLayout(false);
            this.localTabPage.ResumeLayout(false);
            this.artifactTabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button addArtifact;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.TabPage remoteTabPage;
        private System.Windows.Forms.TreeView treeView1;
        private System.Windows.Forms.TabPage localTabPage;
        private System.Windows.Forms.ListView localListView;
        private System.Windows.Forms.ColumnHeader ArtifactNameHeader;
        private System.Windows.Forms.ColumnHeader versionHeader;
        private System.Windows.Forms.TabControl artifactTabControl;
    }
}