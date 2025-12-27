# 记账App - Android应用

一个完全本地运行的Android记账应用，帮助你轻松管理个人财务。

## 功能特性

### 核心功能
- 📊 **月度统计** - 自动计算当月总支出、总收入和结余
- 💰 **收支记录** - 支持添加支出和收入记录，自动汇总计算
- 📅 **日历视图** - 查看每日收支情况（开发中）
- 💵 **预算管理** - 设置下月预算，跟踪预算执行情况
- 📈 **数据分析** - 提供分类统计分析

### 详细功能
1. **记录管理**
   - 添加支出/收入记录
   - 删除记录
   - 分类管理（预设多个常用分类）
   - 备注支持

2. **月度视图**
   - 显示当月总收入、总支出和结余
   - 按时间倒序显示所有记录

3. **统计分析**
   - 支出分类统计
   - 收入分类统计
   - 金额排序

4. **预算功能**
   - 设置预计支出和收入
   - 预算执行进度条
   - 实时跟踪预算使用情况

## 技术栈

- **Kotlin** - 编程语言
- **Jetpack Compose** - 现代化UI框架
- **Room Database** - 本地数据库存储
- **Material Design 3** - UI设计规范
- **MVVM架构** - ViewModel + LiveData/StateFlow

## 编译和运行

### 前置要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK (minSdk 24, targetSdk 34)

### 编译步骤

1. 使用Android Studio打开项目

2. 同步Gradle依赖
   - Android Studio会自动同步，或者点击 `File -> Sync Project with Gradle Files`

3. 运行应用
   - 连接Android设备或启动模拟器
   - 点击 `Run` 按钮或按 `Shift+F10`

4. 生成APK
   - `Build -> Build Bundle(s) / APK(s) -> Build APK(s)`
   - APK文件会在 `app/build/outputs/apk/debug/` 目录下

## 项目结构

```
src/main/java/com/example/recording_app/
├── data/              # 数据层
│   ├── Record.kt      # 记录数据模型
│   ├── Budget.kt      # 预算数据模型
│   ├── Category.kt    # 分类数据模型
│   ├── RecordDao.kt   # 记录数据访问对象
│   ├── BudgetDao.kt   # 预算数据访问对象
│   └── AppDatabase.kt # Room数据库
├── ui/
│   ├── screen/        # 界面组件
│   │   ├── MainScreen.kt
│   │   ├── RecordsScreen.kt
│   │   ├── CalendarScreen.kt
│   │   ├── StatisticsScreen.kt
│   │   ├── BudgetScreen.kt
│   │   └── AddRecordDialog.kt
│   ├── theme/         # 主题配置
│   └── viewmodel/     # ViewModel
│       └── FinanceViewModel.kt
└── MainActivity.kt    # 主Activity
```

## 数据存储

所有数据都存储在设备的Room数据库中，完全本地运行，不会上传到任何服务器。

- **数据安全**：所有数据仅保存在本地数据库
- **无需网络**：离线完全可用
- **隐私保护**：不会收集任何个人信息

## 使用说明

### 添加记录
1. 点击右下角的 **+** 按钮
2. 选择类型（支出/收入）
3. 输入金额
4. 选择分类
5. 添加备注（可选）
6. 点击"保存"

### 查看统计
- 切换到"统计"标签页
- 查看支出和收入的分类统计

### 设置预算
1. 切换到"预算"标签页
2. 输入预计支出和收入
3. 点击"保存预算"
4. 查看预算进度条

## 注意事项

- 数据存储在本地数据库，卸载应用会导致数据丢失
- 建议定期备份数据库文件
- 支持Android 7.0 (API 24) 及以上版本

## 许可证

MIT License
