# 编译说明

这是一个Android原生应用项目，使用Kotlin和Jetpack Compose开发。

## 编译要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK (minSdk 24, targetSdk 34)

## 编译步骤

1. **打开项目**
   - 使用Android Studio打开项目目录

2. **同步Gradle**
   - Android Studio会自动同步依赖
   - 或者手动点击 `File -> Sync Project with Gradle Files`

3. **生成APK**
   
   **Debug版本：**
   ```bash
   ./gradlew assembleDebug
   ```
   或通过Android Studio: `Build -> Build Bundle(s) / APK(s) -> Build APK(s)`
   
   APK位置: `app/build/outputs/apk/debug/app-debug.apk`

   **Release版本：**
   ```bash
   ./gradlew assembleRelease
   ```
   APK位置: `app/build/outputs/apk/release/app-release.apk`

4. **安装到设备**
   - 连接Android设备并启用USB调试
   - 运行 `./gradlew installDebug` 或通过Android Studio直接运行

## 注意事项

- 首次编译可能需要较长时间下载依赖
- 确保网络连接正常以下载Gradle依赖
- Release版本需要签名配置（可选，Debug版本可以直接安装）

