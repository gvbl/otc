package com.otc.application

import Desk
import Person
import Space

fun isValidPerson(person: Person): Boolean = person.name.length in 1..80

fun isValidDesk(desk: Desk): Boolean = desk.name.length in 1..80

fun isValidSpaceName(space: Space): Boolean = space.name.length in 1..80