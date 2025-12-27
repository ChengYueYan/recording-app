# 记账App / Recording App

[中文](#中文) | [English](#english)

---

## 中文

一个完全本地运行的Android记账应用，帮助你轻松管理个人财务。

### 功能特性

#### 核心功能
- 📊 **月度统计** - 自动计算当月总支出、总收入和结余
- 💰 **收支记录** - 支持添加支出和收入记录，自动汇总计算
- 📅 **日历视图** - 查看每日收支情况
- 💵 **预算管理** - 设置月度预算，跟踪预算执行情况
- 📈 **数据分析** - 提供分类统计分析
- 🎨 **主题自定义** - 支持多种颜色主题和背景图片

#### 详细功能
1. **记录管理**
   - 添加/编辑/删除支出和收入记录
   - 多种预设分类（餐饮、交通、购物等）
   - 自定义备注
   - 左滑快速操作

2. **月度视图**
   - 显示当月总收入、总支出和结余
   - 按时间倒序显示所有记录
   - 实时更新统计数据

3. **统计分析**
   - 支出/收入分类统计
   - 金额排序显示
   - 可视化进度条

4. **预算功能**
   - 设置月度预算目标
   - 实时预算进度跟踪
   - 预算执行情况可视化

5. **个性化设置**
   - 主题颜色自定义
   - 应用图标更换
   - 背景图片设置

### 技术栈
- **Kotlin** - 编程语言
- **Jetpack Compose** - 现代化UI框架
- **Room Database** - 本地数据库存储
- **Material Design 3** - UI设计规范
- **MVVM架构** - ViewModel + StateFlow
- **Coroutines** - 异步处理

### 安装说明

#### 下载APK
1. 前往 [Releases](https://github.com/ChengYueYan/recording-app/releases) 页面
2. 下载最新版本的 `app-release.apk`
3. 在Android设备上打开APK文件
4. 允许"从未知来源安装应用"
5. 按照提示完成安装

#### 系统要求
- Android 7.0 (API 24) 及以上版本
- 建议使用 Android 8.0 以上以获得最佳体验

### 从源码编译

#### 前置要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK (minSdk 24, targetSdk 34)

#### 编译步骤
1. 克隆项目
```bash
   git clone https://github.com/ChengYueYan/recording-app.git
   cd recording-app
```

2. 使用Android Studio打开项目
   - 打开 Android Studio
   - 选择 `File -> Open`
   - 选择项目文件夹

3. 同步Gradle依赖
   - Android Studio会自动同步
   - 或手动点击 `File -> Sync Project with Gradle Files`

4. 运行应用
   - 连接Android设备或启动模拟器
   - 点击 `Run` 按钮或按 `Shift+F10`

5. 生成发布版APK
   - `Build -> Generate Signed Bundle / APK`
   - 选择 `APK`
   - 配置签名密钥（首次需要创建）
   - 选择 `release` 构建类型
   - APK生成在 `app/release/` 目录

### 项目结构
```
recording-app/
├── app/
│   └── src/main/java/com/example/recording_app/
│       ├── data/              # 数据层
│       │   ├── Record.kt      # 记录数据模型
│       │   ├── Budget.kt      # 预算数据模型
│       │   ├── Category.kt    # 分类数据模型
│       │   ├── RecordDao.kt   # 记录数据访问对象
│       │   ├── BudgetDao.kt   # 预算数据访问对象
│       │   └── AppDatabase.kt # Room数据库
│       ├── ui/
│       │   ├── screen/        # 界面组件
│       │   │   ├── MainScreen.kt
│       │   │   ├── RecordsScreen.kt
│       │   │   ├── CalendarScreen.kt
│       │   │   ├── StatisticsScreen.kt
│       │   │   ├── BudgetScreen.kt
│       │   │   ├── SettingsScreen.kt
│       │   │   └── AddRecordDialog.kt
│       │   ├── theme/         # 主题配置
│       │   ├── viewmodel/     # ViewModel
│       │   └── components/    # 可复用组件
│       └── MainActivity.kt    # 主Activity
├── build.gradle              # 项目配置
└── README.md                # 说明文档
```

### 使用说明

#### 添加记录
1. 点击右下角的 **+** 按钮
2. 选择类型（支出/收入）
3. 输入金额
4. 选择分类
5. 添加备注（可选）
6. 点击"保存"

#### 编辑/删除记录
- **编辑**：左滑记录条目，点击"编辑"按钮
- **删除**：左滑记录条目，点击"删除"按钮

#### 查看统计
- 切换到"统计"标签页
- 查看支出和收入的分类统计
- 按金额排序显示

#### 设置预算
1. 切换到"预算"标签页
2. 输入预计支出和收入
3. 点击"保存预算"
4. 实时查看预算进度

#### 个性化设置
1. 切换到"设置"标签页
2. 选择主题颜色
3. 更换应用图标
4. 设置背景图片

### 数据存储
所有数据都存储在设备的Room数据库中，完全本地运行，不会上传到任何服务器。

- **数据安全**：所有数据仅保存在本地数据库
- **无需网络**：离线完全可用
- **隐私保护**：不会收集任何个人信息
- **数据持久化**：数据永久保存，除非手动删除或卸载应用

### 注意事项
- ⚠️ 卸载应用会导致所有数据丢失
- 💾 建议定期导出备份（功能开发中）
- 📱 支持Android 7.0及以上版本
- 🔒 所有数据仅存储在本地，请妥善保管设备

### 已知问题
- 日历视图功能开发中
- 数据导出/导入功能开发中

### 贡献
欢迎提交Issue和Pull Request！

### 许可证
MIT License

---

## English

A fully local Android accounting app that helps you easily manage personal finances.

### Features

#### Core Functions
- 📊 **Monthly Statistics** - Automatically calculate monthly expenses, income, and balance
- 💰 **Transaction Records** - Add expense and income records with automatic summation
- 📅 **Calendar View** - View daily income and expenses
- 💵 **Budget Management** - Set monthly budgets and track execution
- 📈 **Data Analysis** - Category-based statistical analysis
- 🎨 **Theme Customization** - Multiple color themes and background images

#### Detailed Features
1. **Record Management**
   - Add/Edit/Delete expense and income records
   - Multiple preset categories (Food, Transport, Shopping, etc.)
   - Custom notes
   - Swipe for quick actions

2. **Monthly View**
   - Display total income, expenses, and balance for current month
   - Records sorted by time in descending order
   - Real-time statistics updates

3. **Statistical Analysis**
   - Expense/Income category statistics
   - Amount-based sorting
   - Visual progress bars

4. **Budget Features**
   - Set monthly budget targets
   - Real-time budget progress tracking
   - Budget execution visualization

5. **Personalization**
   - Custom theme colors
   - App icon selection
   - Background image settings

### Tech Stack
- **Kotlin** - Programming Language
- **Jetpack Compose** - Modern UI Framework
- **Room Database** - Local Data Storage
- **Material Design 3** - UI Design Guidelines
- **MVVM Architecture** - ViewModel + StateFlow
- **Coroutines** - Asynchronous Processing

### Installation

#### Download APK
1. Go to [Releases](https://github.com/ChengYueYan/recording-app/releases) page
2. Download the latest `app-release.apk`
3. Open the APK file on your Android device
4. Allow "Install from Unknown Sources"
5. Follow the prompts to complete installation

#### System Requirements
- Android 7.0 (API 24) or higher
- Android 8.0+ recommended for best experience

### Build from Source

#### Prerequisites
- Android Studio Hedgehog (2023.1.1) or higher
- JDK 8 or higher
- Android SDK (minSdk 24, targetSdk 34)

#### Build Steps
1. Clone the project
```bash
   git clone https://github.com/ChengYueYan/recording-app.git
   cd recording-app
```

2. Open project in Android Studio
   - Open Android Studio
   - Select `File -> Open`
   - Choose project folder

3. Sync Gradle dependencies
   - Android Studio will sync automatically
   - Or manually click `File -> Sync Project with Gradle Files`

4. Run the app
   - Connect Android device or start emulator
   - Click `Run` button or press `Shift+F10`

5. Generate release APK
   - `Build -> Generate Signed Bundle / APK`
   - Select `APK`
   - Configure signing key (create if first time)
   - Choose `release` build type
   - APK generated in `app/release/` directory

### Project Structure
```
recording-app/
├── app/
│   └── src/main/java/com/example/recording_app/
│       ├── data/              # Data Layer
│       │   ├── Record.kt      # Record data model
│       │   ├── Budget.kt      # Budget data model
│       │   ├── Category.kt    # Category data model
│       │   ├── RecordDao.kt   # Record data access object
│       │   ├── BudgetDao.kt   # Budget data access object
│       │   └── AppDatabase.kt # Room database
│       ├── ui/
│       │   ├── screen/        # UI Components
│       │   │   ├── MainScreen.kt
│       │   │   ├── RecordsScreen.kt
│       │   │   ├── CalendarScreen.kt
│       │   │   ├── StatisticsScreen.kt
│       │   │   ├── BudgetScreen.kt
│       │   │   ├── SettingsScreen.kt
│       │   │   └── AddRecordDialog.kt
│       │   ├── theme/         # Theme configuration
│       │   ├── viewmodel/     # ViewModel
│       │   └── components/    # Reusable components
│       └── MainActivity.kt    # Main Activity
├── build.gradle              # Project configuration
└── README.md                # Documentation
```

### User Guide

#### Add Record
1. Click the **+** button at bottom right
2. Select type (Expense/Income)
3. Enter amount
4. Choose category
5. Add note (optional)
6. Click "Save"

#### Edit/Delete Record
- **Edit**: Swipe left on record item, click "Edit"
- **Delete**: Swipe left on record item, click "Delete"

#### View Statistics
- Switch to "Statistics" tab
- View category-based expense and income statistics
- Sorted by amount

#### Set Budget
1. Switch to "Budget" tab
2. Enter expected expenses and income
3. Click "Save Budget"
4. View real-time budget progress

#### Personalize Settings
1. Switch to "Settings" tab
2. Select theme color
3. Change app icon
4. Set background image

### Data Storage
All data is stored in the device's Room database, running completely locally without uploading to any server.

- **Data Security**: All data stored locally only
- **No Network Required**: Works fully offline
- **Privacy Protection**: No personal information collected
- **Data Persistence**: Data permanently saved unless manually deleted or app uninstalled

### Notes
- ⚠️ Uninstalling the app will result in data loss
- 💾 Regular backups recommended (feature in development)
- 📱 Supports Android 7.0 and above
- 🔒 All data stored locally only, keep your device secure

### Known Issues
- Calendar view feature in development
- Data export/import feature in development

### Contributing
Issues and Pull Requests are welcome!

### License
MIT License

---

## Screenshots / 应用截图

_Coming soon / 即将添加_

## Contact / 联系方式

- GitHub: [@ChengYueYan](https://github.com/ChengYueYan)
- Issues: [Report a bug](https://github.com/ChengYueYan/recording-app/issues)