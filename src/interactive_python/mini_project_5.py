# implementation of card game - Memory
__author__ = 'vvlasov'
import simplegui
import random


CANVAS_WIDTH = 800
CANVAS_HEIGHT = 120

UPPER_BOUND = 8
data = range(UPPER_BOUND) + range(UPPER_BOUND)
open_mask = [False for item in data]
current_turn = []
attempts = 0


# helper function to initialize globals
def get_exposed():
    return [open_mask[index] or index in current_turn for index in range(len(data))]


def init():
    global open_mask, attempts, current_turn
    random.shuffle(data)
    open_mask = [False for item in data]
    attempts = 0
    current_turn = []
    label.set_text("Moves = 0")


# define event handlers
def mouseclick(pos):
    global current_turn, attempts
    exposed = get_exposed()

    card_width = CANVAS_WIDTH / len(data)
    card_index = int(pos[0] / card_width)

    if not exposed[card_index]:
        label.set_text("Moves = " + str(attempts // 2 + 1))
        attempts += 1
        if len(current_turn) < 2:
            current_turn.append(card_index)
        else:
            open_mask[current_turn[0]] = open_mask[current_turn[1]] = data[current_turn[0]] == data[current_turn[1]]
            current_turn = [card_index]


def toggle_expose_all():
    global open_mask
    open_mask = [True for i in range(len(data))]

# cards are logically 50x100 pixels in size
def draw(canvas):
    global open_mask

    card_len = len(data)
    card_width = CANVAS_WIDTH / card_len

    exposed = get_exposed()
    for index in range(card_len):
        card = data[index]
        expose = exposed[index]
        if expose:
            canvas.draw_line((card_width * index + card_width / 2, 0),
                             (card_width * index + card_width / 2, CANVAS_HEIGHT),
                             card_width, "green")
            canvas.draw_text(str(card), [card_width * index + card_width / 2 - 10, CANVAS_HEIGHT / 2 + 17], 34, "white")
        else:
            canvas.draw_line((card_width * index + card_width / 2, 0),
                             (card_width * index + card_width / 2, CANVAS_HEIGHT),
                             card_width, "black")

        canvas.draw_line(((card_width * (index + 1) - 1), 0), ((card_width * (index + 1) - 1), CANVAS_HEIGHT),
                         1, "white")
        canvas.draw_line(((card_width * index), 0), ((card_width * index), CANVAS_HEIGHT),
                         1, "white")
        canvas.draw_line((0, 0), (CANVAS_WIDTH, 0), 1, "white")
        canvas.draw_line((0, CANVAS_HEIGHT), (CANVAS_WIDTH, CANVAS_HEIGHT), 1, "white")


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", CANVAS_WIDTH, CANVAS_HEIGHT)
frame.add_button("Restart", init)
frame.add_button("Give up", toggle_expose_all)
label = frame.add_label("Moves = 0")

# initialize global variables
init()

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
frame.start()


# Always remember to review the grading rubric