const mongoose = require("mongoose")

const schema = new mongoose.Schema({
  title: String,
  status: String,
  assignedTo: String,
  daysPending: Number
})

module.exports = mongoose.model("Task", schema)