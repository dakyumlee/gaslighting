package com.gaslab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    
    // @GetMapping("/")
    // public String home() {
    //     return "forward:/index.html";
    // }
    
    @GetMapping("/test")
    public String test() {
        return "앱이 정상 동작중입니다! ✅";
    }
    
    @GetMapping("/debug")
    public String debug() {
        return """
            <h2>디버그 정보</h2>
            <ul>
                <li><a href="/api/situations">상황 목록 (현재 결과)</a></li>
                <li>DB 연결: 성공 (빈 배열이 나오니까 연결됨)</li>
                <li>문제: JPA가 데이터를 못 찾음</li>
            </ul>
            <p>Oracle에서 테이블명과 데이터를 확인해주세요!</p>
            """;

            
    }

@GetMapping("/")
public String home() {
    return """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
          <meta charset="UTF-8">
          <title>가스라이팅 실험실</title>
          <style>
            @font-face {
              font-family: 'ChosunGu';
              src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@1.0/ChosunGu.woff') format('woff');
              font-weight: normal;
              font-style: normal;
            }

            body {
              font-family: 'ChosunGu', sans-serif;
              background-color: #fff7f0;
              margin: 0;
              padding: 0;
            }

            .container {
              display: flex;
              flex-direction: column;
              height: 100vh;
              max-width: 600px;
              margin: auto;
              padding: 20px;
            }

            h1 {
              text-align: center;
              margin-bottom: 10px;
            }

            .chat-window {
              flex: 1;
              overflow-y: auto;
              display: flex;
              flex-direction: column;
              padding: 10px;
              background-color: #fff3f3;
              border-radius: 12px;
            }

            .message {
              max-width: 70%;
              padding: 10px 15px;
              margin: 10px 0;
              border-radius: 15px;
              font-size: 1rem;
              white-space: pre-line;
            }

            .ai {
              background: #ffd2d2;
              align-self: flex-start;
            }

            .user {
              background: #c7ffd2;
              align-self: flex-end;
            }

            .input-box {
              display: flex;
              gap: 10px;
              margin-top: 10px;
            }

            input {
              flex: 1;
              padding: 12px;
              border-radius: 12px;
              border: 1px solid #ccc;
              font-family: 'ChosunGu';
            }

            button {
              padding: 12px 20px;
              border: none;
              border-radius: 12px;
              background-color: #ff8989;
              color: white;
              font-family: 'ChosunGu';
              cursor: pointer;
            }

            button:hover {
              background-color: #e26b6b;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <h1>🧪 가스라이팅 실험실 🧪</h1>

            <div class="chat-window" id="chatWindow">
              <div class="message ai">왜 이렇게 늦게 연락했어?</div>
            </div>

            <div class="input-box">
              <input type="text" id="userInput" placeholder="당신의 반응은?">
              <button onclick="sendReply()">보내기</button>
            </div>
          </div>

          <script>
            function appendMessage(text, sender) {
              const msg = document.createElement('div')
              msg.className = 'message ' + sender
              msg.innerText = text
              document.getElementById('chatWindow').appendChild(msg)
              msg.scrollIntoView({ behavior: 'smooth' })
            }

            async function sendReply() {
                const input = document.getElementById('userInput')
                const text = input.value.trim()
                if (!text) return
              
                appendMessage(text, 'user')
              
                await fetch('/api/dialogue/save', {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ sender: 'user', message: text })
                })
              
                const res = await fetch('/api/dialogue/generate', {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ text })
                })
                const data = await res.json()
                appendMessage(data.aiReply, 'ai')
              
                input.value = ''
              }
              
          </script>
        </body>
        </html>
        """;
}
}