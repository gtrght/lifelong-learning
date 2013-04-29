import random
import math

upper_bound = 0
goal = 0
attempts = 0


def init(max_range):
    global goal, attempts, upper_bound
    upper_bound = max_range
    goal = random.randrange(0, max_range)
    attempts = math.ceil(math.log(max_range, 2))
    print 'New game. Range is from 0 to', max_range
    dump_number_of_remaining()
    print ''


def range100():
    init(100)
    # button that changes range to range [0,100) and restarts


def range1000():
    init(1000)
    # button that changes range to range [0,1000) and restarts


def check_input(guess):
    return goal - guess


def dump_number_of_remaining():
    print 'Number of remaining guesses is', attempts


def check_user_input(guess):
    global attempts
    print 'Guess was', guess
    attempts -= 1

    check = check_input(guess)
    if attempts <= 0 and check != 0:
        print 'No attempts left\n'
        init(upper_bound)
        return

    dump_number_of_remaining()
    if check == 0:
        print 'Correct!\n'
        init(upper_bound)
    elif check > 0:
        print 'Higher'
    else:
        print 'Lower'

    print ''


def get_input(guess):
    if guess.isDigit():
        check_user_input(int(guess))
    else:
        print 'User input is not a number', guess


f = simplegui.create_frame("Guess the number",  200, 200)
f.add_button("Range is [0 to 100)", range100, 200)
f.add_button("Range is [0 to 1000)", range1000, 200)
f.add_input("Enter a guess", get_input, 200)

f.start()
range100()