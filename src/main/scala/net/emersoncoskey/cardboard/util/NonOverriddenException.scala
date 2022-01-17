package net.emersoncoskey.cardboard.util

class NonOverriddenException private (msg: String) extends Exception

object NonOverriddenException {
	def apply(member: String) = new NonOverriddenException(s"Abstract member $member not overridden in deriving class")
}
