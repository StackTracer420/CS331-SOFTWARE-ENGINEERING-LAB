const router = require("express").Router()
const User = require("../models/User")
const bcrypt = require("bcrypt")
const jwt = require("jsonwebtoken")

const SECRET = "flowforge_secret"

router.post("/register", async (req, res) => {
  const hash = await bcrypt.hash(req.body.password, 10)
  const user = new User({ ...req.body, password: hash })
  await user.save()
  res.send("Registered")
})

router.post("/login", async (req, res) => {
  const user = await User.findOne({ username: req.body.username })
  if (!user) return res.status(400).send("No user")

  const valid = await bcrypt.compare(req.body.password, user.password)
  if (!valid) return res.status(400).send("Wrong password")

  const token = jwt.sign({ id: user._id, role: user.role }, SECRET)
  res.json({ token })
})

module.exports = router