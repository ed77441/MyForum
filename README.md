# MyForum
using java ee to build a sample forum-like website

Demo影片: https://youtu.be/Zdyjf66rV2E

架構解釋

  1. filter - url parameter filter<br />
     如果url參數不符合 servlet所設定的分別get post put delete參數規則的話 重新導向到錯誤頁面<br />
     否則filter就把按照規則把參數 轉換成想要的型態型態 並塞到 request attribute裡 交給servlet<br />
  2. servlet (controller) - 讀出參數並交給service<br />
     如果service 執行失敗 重新導向到錯誤頁面<br />
     否則執行以下流程<br />
         如果是get method 就交給 service讀取相應 POJO class物件 (model) 並丟給 jsp (view)<br />
         否則 交給 service 更新資料表 並重新導向到其他頁面<br />
  3. service - 驗證資料格式和檢查此執行動作合法的 (例如如果某人想改變別人的留言就會失敗)<br />
     如果參數格式正確 交給 dao 處理<br />
     否則執行失敗<br />
  4. dao 使用 jdbc 與資料庫互動<br />

功能介紹

  1. 可以註冊帳號、發文、留言
  2. 可以查詢文章標題字串或藉由主題來分類文章
  3. 可以編結個人資料、文章或留言
  4. 當有人在你發的文下留言 你會收到通知 (long polling)
