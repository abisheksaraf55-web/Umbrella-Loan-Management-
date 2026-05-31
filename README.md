# Loan Manager 2080 — Android App

## Quick Build (2 Methods)

### Method 1: Android Studio (Recommended - 5 minutes)
1. Install [Android Studio](https://developer.android.com/studio) (free)
2. Open Android Studio → **Open** → select this `LoanManager` folder
3. Wait for Gradle sync to complete (~2 min first time)
4. Click **Build → Build Bundle(s)/APK(s) → Build APK(s)**
5. APK saved at: `app/build/outputs/apk/debug/app-debug.apk`
6. Transfer APK to your Android phone and install

### Method 2: Online Builder (No installation needed)
1. Zip this entire `LoanManager` folder
2. Go to [GitHub](https://github.com) → New repository → Upload files
3. Use [GitHub Actions](https://docs.github.com/actions) to auto-build APK
4. OR use [AppGyver](https://www.appgyver.com/) / [Appetize.io](https://appetize.io/)

### Method 3: Install as App via Browser (Instant - No APK needed!)
1. Open the `index.html` file URL in Chrome on Android
2. Tap the **⋮ menu** → **Add to Home Screen**
3. The app installs as a full-screen app on your home screen ✅

---

## App Features
- 2,111 loan entries from your Excel sheet
- BS (Bikram Sambat) date calculations
- Interest: Amount × Rate × Days ÷ 30
- Close entries with profit/loss calculation
- Undo system for accidental closes
- Offline — no internet needed
- Works on all Android 5.0+ phones

## APK Details
- Package: `com.loanmanager.app`
- Min Android: 5.0 (API 21)
- Target Android: 14 (API 34)
- Permissions: Storage (for CSV export)
