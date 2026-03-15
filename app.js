class Task{

constructor(id,description){

this.id=id
this.description=description
this.status="Pending"
this.assignedTo=null
this.daysPending=Math.floor(Math.random()*6)

}

assign(user){

this.assignedTo=user
this.status="Assigned"

log("Task '"+this.description+"' assigned to "+user)

updateTable()

}

complete(){

if(this.assignedTo==null){

log("Task '"+this.description+"' cannot be completed")

return

}

this.status="Completed"

log("Task '"+this.description+"' completed")

updateTable()

}

}

class Workflow{

constructor(id,name){

this.id=id
this.name=name
this.status="Created"
this.tasks=[]

}

addTask(task){

this.tasks.push(task)

}

start(){

this.status="In Progress"

log("Workflow started")

updateStats()

}

complete(){

let done=this.tasks.every(t=>t.status==="Completed")

if(done){

this.status="Completed"

log("Workflow completed successfully")

}

updateStats()

}

}

class EscalationHandler{

check(task){

if(task.status!=="Completed" && task.daysPending>3){

log("⚠ Escalation detected for "+task.id)

showPopup("Escalation: "+task.id)

}

}

}

let workflow=new Workflow("WF101","Document Approval")

let t1=new Task("T1","Draft Document")
let t2=new Task("T2","Review Document")
let t3=new Task("T3","Final Approval")

workflow.addTask(t1)
workflow.addTask(t2)
workflow.addTask(t3)

let escalation=new EscalationHandler()

let index=0

function startWorkflow(){

workflow.start()

}

function assignTasks(){

t1.assign("UserA")
t2.assign("UserB")
t3.assign("Manager")

}

function completeTask(){

if(index<workflow.tasks.length){

workflow.tasks[index].complete()

index++

}else{

workflow.complete()

}

}

function checkEscalation(){

workflow.tasks.forEach(t=>escalation.check(t))

}

function showDetails(){

workflow.tasks.forEach(t=>{

log(t.id+" | "+t.description+" | "+t.status)

})

}

function resetWorkflow(){

workflow.tasks.forEach(t=>{

t.status="Pending"
t.assignedTo=null

})

workflow.status="Created"
index=0

document.getElementById("log").innerHTML=""

updateTable()
updateStats()

}

function log(msg){

let logBox=document.getElementById("log")

let time=new Date().toLocaleTimeString()

logBox.innerHTML+=`[${time}] ${msg}<br>`

logBox.scrollTop=logBox.scrollHeight

}

function showPopup(text){

let popup=document.getElementById("popup")

popup.innerText=text
popup.style.display="block"

setTimeout(()=>popup.style.display="none",3000)

}

function searchTask(){

let input=document.getElementById("searchBox").value.toLowerCase()

let rows=document.querySelectorAll("#taskTable tbody tr")

rows.forEach(row=>{

row.style.display=row.textContent.toLowerCase().includes(input) ? "" : "none"

})

}

function updateStats(){

let total=workflow.tasks.length
let completed=workflow.tasks.filter(t=>t.status==="Completed").length

document.getElementById("totalTasks").textContent=total
document.getElementById("completedTasks").textContent=completed
document.getElementById("pendingTasks").textContent=total-completed
document.getElementById("workflowStatus").textContent=workflow.status

let percent=(completed/total)*100

document.getElementById("progressFill").style.width=percent+"%"

}

function updateTable(){

let tbody=document.querySelector("#taskTable tbody")

tbody.innerHTML=""

workflow.tasks.forEach(t=>{

let statusClass=t.status.toLowerCase()

let row=`
<tr>
<td>${t.id}</td>
<td>${t.description}</td>
<td><span class="status ${statusClass}">${t.status}</span></td>
<td>${t.assignedTo ?? "Not Assigned"}</td>
</tr>
`

tbody.innerHTML+=row

})

updateStats()

}

updateTable()