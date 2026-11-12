IP FINDER PRO — Android APK Project

এই প্রজেক্টে আছে:
- নিজের Public IP auto detect
- যেকোনো IP manual lookup
- API: https://nid.proo.one/ip-info.php?ip=IP_ADDRESS
- Google Maps-এ আনুমানিক অবস্থান খোলা
- সর্বশেষ ৫০টি lookup-এর history (ফোনেই সংরক্ষিত)

APK বানানোর সহজতম পদ্ধতি (মোবাইল দিয়েও করা যাবে):
1. GitHub-এ একটি account খুলুন / লগইন করুন।
2. New repository তৈরি করুন, নাম দিন IPFinderPro।
3. এই ZIP ফাইলটি Extract করে ভেতরের সব ফাইল GitHub repository-তে upload করুন। .github ফোল্ডারটিও upload করতে হবে।
4. Upload/commit করার পর GitHub repository-র Actions tab খুলুন।
5. "Build Android APK" workflow চলবে। না চললে Actions থেকে workflow খুলে "Run workflow" চাপুন।
6. কাজ শেষ হলে workflow run-এর নিচে Artifacts থেকে IP-Finder-Pro-APK ডাউনলোড করুন।
7. ZIP extract করে app-debug.apk ফাইলে চাপ দিয়ে install করুন। Android চাইলে browser/files app-কে install permission দিন।

গুরুত্বপূর্ণ:
- এটি একটি প্রাথমিক MVP; API অনলাইন থাকতে হবে।
- Auto IP-র জন্য api.ipify.org থেকে public IP নেওয়া হয়, তারপর BMT API-তে lookup হয়।
- API response-এ থাকা coordinates আনুমানিক; এগুলো কোনো ব্যক্তির সঠিক বাসার ঠিকানা নয়।
- History এই অ্যাপের local storage-এ থাকে।
- Google Maps লিংক external browser/maps app-এ খোলে।
- Build workflow প্রথমবারে কোনো dependency/Gradle সমস্যা দেখালে error screenshot পাঠান; সেটি ঠিক করতে সাহায্য করা যাবে।
