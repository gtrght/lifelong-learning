__author__ = 'vvlasov'

import random


def number_to_name_et(number):
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


def name_to_number_et(name):
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


def rpsls_ethalon(name, computer_guess):
    human_guess = name_to_number_et(name)

    if human_guess == computer_guess:
        return "Player and computer tie!"
    else:
        delta = abs(computer_guess - human_guess)  ## Calculating the abs
        if delta <= 2:
            human_win = computer_guess < human_guess
        else:
            human_win = computer_guess > human_guess

        return ("Player" if human_win else "Computer") + " wins!"


import random

# helper functions

def number_to_name(number):
    # convert number to a name using if/elif/else
    # don't forget to return the result!

    if number == 0:
        return 'rock'
    elif number == 1:
        return 'Spock'
    elif number == 2:
        return 'paper'
    elif number == 3:
        return 'lizard'
    elif number == 4:
        return 'scissors'
    else:
        return 'Not a valid number. Chose again <0,4>.'


def name_to_number(name):
    # convert name to number using if/elif/else
    # don't forget to return the result!

    if name == 'rock':
        return 0
    elif name == 'Spock':
        return 1
    elif name == 'paper':
        return 2
    elif name == 'lizard':
        return 3
    elif name == 'scissors':
        return 4
    else:
        return 'Not a valid name. Chose again (rock, Spock, paper, lizard, scissors).'

def rpsls(name):
    # convert name to player_number using name_to_number
    player_number = name_to_number(name)

    # compute random guess for comp_number using random.randrange()
    for comp_number  in range(0, 5):

        # compute difference of player_number and comp_number modulo five
        modulo_eq = (player_number - comp_number) % 5

        # use if/elif/else to determine winner
        if modulo_eq == 0:
            winner = 0
        elif modulo_eq == 1 or modulo_eq == 2:
            winner = 'Player'
        elif modulo_eq == 3 or modulo_eq  == 4:
            winner = 'Computer'

        # convert comp_number to name using number_to_name
        comp_name = number_to_name(comp_number)

        # print results
        print('\n')
        print('Player chooses ' + name)
        print('Computer chooses ' + comp_name)

        strres = ''

        if winner != 0:
            strres = winner + ' wins!'
        else:
            strres = 'Player and computer tie!'


        if strres != rpsls_ethalon(name, comp_number):
            raise ValueError(" {} {} ".format(name, number_to_name_et(comp_number)))


# test your code
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")