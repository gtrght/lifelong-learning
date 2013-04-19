# Rock-paper-scissors-lizard-Spock template


# The key idea of this program is to equate the strings
# "rock", "paper", "scissors", "lizard", "Spock" to numbers
# as follows:
#
# 0 - rock
# 1 - Spock
# 2 - paper
# 3 - lizard
# 4 - scissors

# helper functions
import math
import random


def number_to_name(number):
    if number == 0:
        return "rock"
    elif number == 1:
        return "Spock"
    elif number == 2:
        return "paper"
    elif number == 3:
        return "lizard"
    elif number == 4:
        return "scissors"
    else:
        return None


def name_to_number(name):
    if name == "rock":
        return 0
    elif name == "Spock":
        return 1
    elif name == "paper":
        return 2
    elif name == "lizard":
        return 3
    elif name == "scissors":
        return 4
    else:
        return None

## Testing the code
# for i in xrange(0, 5):
#     name = number_to_name(i)
#     number = name_to_number(name)
#     print "number_to_name({", i, "}) = {", name, "}, name_to_number({", name, "}) = {", number, "} "


def rpsls(name):
    human_guess = name_to_number(name)
    computer_guess = random.randrange(0, 5)

    print "Player chooses", name
    print "Computer chooses", number_to_name(computer_guess)
    if human_guess == computer_guess:
        print "Player and computer tie!\n"
    else:
        delta = abs(computer_guess - human_guess)  ## Calculating the abs
        if delta <= 2:
            human_win = computer_guess < human_guess
        else:
            human_win = computer_guess > human_guess

        print "Player" if human_win else "Computer", "wins!\n"


# test your code
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")


