# Installation Guide #
This page guides you through installing and running the Android application from source in Eclipse.

<br>

<h2>Plugins and SDKs Required</h2>

External plugins required:<br>
<br>
<ul><li>Android Developer Tools (ADT) for eclipse<br>
</li><li>Google Plugin</li></ul>

Libraries required:<br>
<ul><li>Android 4.0. SDK<br>
</li><li>Google Web Toolkit SDK (GWT)</li></ul>

Installation and configuring of these plugins is given on:<br>
<a href='https://developers.google.com/eclipse/docs/appeng_android_install_setup'>https://developers.google.com/eclipse/docs/appeng_android_install_setup</a>

<br>

<h2>Checking out Project</h2>
The Android and Google App Engine projects can be imported using SVN and the following checkout URL:<br>
<br>
<a href='http://rich-2012-cafe.googlecode.com/svn/trunk/'>http://rich-2012-cafe.googlecode.com/svn/trunk/</a>

These projects are named Rich2012Cafe-Android and Rich2012Cafe-AppEngine respectively.<br>
<br>
<br>

<h2>Prior to Running</h2>
The following steps are required before running the application.<br>
<br>
<h3>Add shared source folder to Android project</h3>
<ul><li>Select the Android project.<br>
</li><li>Right click and select New > Folder.<br>
</li><li>Ensure the Android project is selected.<br>
</li><li>Select Advanced and then link to alternate location (Linked folder)<br>
</li><li>Select Browse and choose the shared folder within the Google App Engine project.<br>
</li><li>The shared folder will have now been added to the Android project.<br>
</li><li>If the shared folder isn't a source folder then:<br>
<ul><li>Select the Android project<br>
</li><li>Right click and select New > Source Folder.<br>
</li><li>For Folder Name, click Select and choose the shared folder.</li></ul></li></ul>

<h3>Setting Annotations Factory</h3>
<ul><li>Select the Android project.<br>
</li><li>Right click and select Properties.<br>
</li><li>Go to Java Compiler > Annotation Processing > Factory Path.<br>
</li><li>Select Edit and choose the location of our requestFactory-apt.jar (This is in the GWT eclipse plugin folder)</li></ul>

<br>

<h2>Running the Project</h2>

<ul><li>Select the Android project.<br>
</li><li>Right click and select Debug As > Remote App Engine Connected Android Application.