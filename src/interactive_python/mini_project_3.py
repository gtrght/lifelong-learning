__author__ = 'vvlasov'


# template for "Stopwatch: The Game"

# define global variables

MINUTE_FACTOR = 60
SECOND_FACTOR = 10

time = 0
wins = 0
total = 0
running = False


def format(t):
    minutes = t / (MINUTE_FACTOR * SECOND_FACTOR)
    seconds = (t - minutes * (MINUTE_FACTOR * SECOND_FACTOR)) / SECOND_FACTOR
    tens_of_seconds = t - minutes * (MINUTE_FACTOR * SECOND_FACTOR) - seconds * SECOND_FACTOR

    return str(minutes) + ":" + (str(seconds) if seconds > 9 else "0" + str(seconds)) + "." + str(tens_of_seconds)


def tick():
    global time
    time += 1 if running else 0


def reset():
    global time, wins, total
    time = wins = total = 0
    running = False


def start():
    global running
    running = True


def stop():
    global running, time, wins, total
    if running:
        total += 1
        wins += 1 if time % SECOND_FACTOR == 0 else 0
    running = False

# define event handlers for buttons; "Start", "Stop", "Reset"


# define event handler for timer with 0.1 sec interval


# define draw handler


# create frame


# register event handlers


# start frame


# Please remember to review the grading rubric

