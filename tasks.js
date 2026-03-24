const router = require("express").Router()
const Task = require("../models/Task")

router.get("/", async (req, res) => {
  res.json(await Task.find())
})

router.post("/", async (req, res) => {
  const task = new Task(req.body)
  await task.save()
  res.json(task)
})

router.put("/:id", async (req, res) => {
  const updated = await Task.findByIdAndUpdate(req.params.id, req.body, { new: true })
  res.json(updated)
})

module.exports = router