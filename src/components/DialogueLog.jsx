import { useEffect, useState } from 'react'
import { motion } from 'framer-motion'

export default function DialogueLog() {
  const [logs, setLogs] = useState([])

  useEffect(() => {
    fetch('/api/dialogue/logs') 
      .then((res) => res.json())
      .then((data) => setLogs(data))
      .catch((err) => console.error('로그 가져오기 실패', err))
  }, [])

  return (
    <div className="min-h-screen p-6 font-[ChosunGu] bg-[#fefcf9] text-[#333]">
      <h1 className="text-4xl mb-6 text-center">📜 대화 로그</h1>
      <div className="space-y-4 max-w-2xl mx-auto">
        {logs.map((entry, i) => (
          <motion.div
            key={i}
            className="bg-white shadow-md p-4 rounded-2xl border border-yellow-200"
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3 }}
          >
            <p><b>{entry.sender === 'user' ? '나' : 'AI'}:</b> {entry.message}</p>
          </motion.div>
        ))}
      </div>
    </div>
  )
}
