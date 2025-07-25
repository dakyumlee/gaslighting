document.addEventListener("DOMContentLoaded", async () => {
    const situationSelect = document.getElementById("situation-select")
    const statementList = document.getElementById("statement-list")
    const dialogueList = document.getElementById("dialogue-list")
  
    const situations = await fetch("/api/situations").then(res => res.json())
    situations.forEach(sit => {
      const option = document.createElement("option")
      option.value = sit.name
      option.textContent = sit.name
      situationSelect.appendChild(option)
    })
  
    situationSelect.addEventListener("change", async () => {
      const situation = situationSelect.value
  
      const statements = await fetch(`/api/statements?situation=${situation}`).then(res => res.json())
      statementList.innerHTML = ""
      statements.forEach(content => {
        const li = document.createElement("li")
        li.textContent = content
        statementList.appendChild(li)
      })
  
      const dialogues = await fetch(`/api/dialogues?situation=${situation}`).then(res => res.json())
      dialogueList.innerHTML = ""
      dialogues.forEach(d => {
        const li = document.createElement("li")
        li.textContent = `${d.speaker}: ${d.content}`
        dialogueList.appendChild(li)
      })
    })
  })
  