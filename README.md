新卒の頃に勉強で作ったyoutube関連アプリケーション  
右も左も分からない時にAPIキー普通にコミットしてプライベートリポジトリにしていた。  
キーをローテーションしたため、パブリックリポジトリへ機能を追加して復活

利用方法
google cloude で apiキーを取得後、resourceに.envファイルとして保存
```
youtubeApiKey=**********
```
gradlew run で実行。  
youtubeでライブ配信中のidを標準入力から入力
