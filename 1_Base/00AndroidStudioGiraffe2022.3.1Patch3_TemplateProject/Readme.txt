20231130
1、该项目是使用AndroidStudioGiraffe2022.3.1Patch3创建的适用于《Android编程权威指南-第3版》的工程模板项目
2、需要做的修改项包括：
-所有build.gradle
-所有gradle.properties
-gradle-wrapper.properties
-settings.gradle
-AndroidManifest.xml
-themes.xml

3、需要自己单独添加：
-Activity，通过添加Java类的方式才能添加；无法通过New-Activity的方式添加，因为①最低支持版本需要21，我们的最低支持版minSdkVersion为19
② 需要添加androidx依赖，我们没有androidx依赖
-res下的layout文件夹




