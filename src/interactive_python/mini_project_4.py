# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
PADDLE_VELOCITY = 12
WIDTH = 600
HEIGHT = 400
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2

ball_pos = [WIDTH / 2, HEIGHT / 2]
ball_vel = [0, 0]
score = [0, 0]


def ball_init(right):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos = [WIDTH / 2, HEIGHT / 2]
    ball_vel = [random.randrange(6, 12), random.randrange(3, 9)]

    ball_vel = [-ball_vel[0], -ball_vel[1]] if right else ball_vel


# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are floats
    global score

    paddle2_pos = paddle1_pos = (HEIGHT - PAD_HEIGHT) / 2
    paddle1_vel = paddle2_vel = 0
    score = [0, 0]

    ball_init(random.randint(1, 2) == 2)


def draw(canvas):
    global score, paddle1_pos, paddle2_pos, ball_pos, ball_vel

    # update paddle's vertical position, keep paddle on the screen

    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0], [WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0], [PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0], [WIDTH - PAD_WIDTH, HEIGHT], 1, "White")

    # draw paddles
    canvas.draw_line([PAD_WIDTH / 2, paddle1_pos], [PAD_WIDTH / 2, paddle1_pos + PAD_HEIGHT], PAD_WIDTH, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH / 2, paddle2_pos], [WIDTH - PAD_WIDTH / 2, paddle2_pos + PAD_HEIGHT], PAD_WIDTH,
                     "White")

    canvas.draw_text(str(score[0]), [WIDTH / 2 - 100, 50], 40, "#ccc")
    canvas.draw_text(str(score[1]), [WIDTH / 2 + 100, 50], 40, "#ccc")

    # draw ball and scores
    canvas.draw_circle(ball_pos, BALL_RADIUS, 2, "White", "#aaa")


def keydown(key):
    global paddle1_vel, paddle2_vel

    if key == simplegui.KEY_MAP["w"]:
        paddle1_vel = -PADDLE_VELOCITY
    elif key == simplegui.KEY_MAP["s"]:
        paddle1_vel = PADDLE_VELOCITY
    elif key == simplegui.KEY_MAP["up"]:
        paddle2_vel = -PADDLE_VELOCITY
    elif key == simplegui.KEY_MAP["down"]:
        paddle2_vel = PADDLE_VELOCITY


def keyup(key):
    global paddle1_pos, paddle1_vel, paddle2_pos, paddle2_vel

    if key == simplegui.KEY_MAP["w"] or key == simplegui.KEY_MAP["s"]:
        paddle1_vel = 0
    elif key == simplegui.KEY_MAP["down"] or key == simplegui.KEY_MAP["up"]:
        paddle2_vel = 0


def tick():
    global ball_pos, ball_vel, paddle1_pos, paddle2_pos
    paddle1_pos = max(0, min(HEIGHT - PAD_HEIGHT, paddle1_pos + paddle1_vel))
    paddle2_pos = max(0, min(HEIGHT - PAD_HEIGHT, paddle2_pos + paddle2_vel))

    ball_pos = [ball_pos[0] + ball_vel[0], ball_pos[1] + ball_vel[1]]

    #checking bounds and reflections
    if ball_pos[1] <= BALL_RADIUS or ball_pos[1] >= HEIGHT - BALL_RADIUS:
        ball_vel[1] = -ball_vel[1]

    if ball_pos[0] <= BALL_RADIUS + PAD_WIDTH:
        if paddle1_pos < ball_pos[1] < paddle1_pos + PAD_HEIGHT:
            ball_vel[0] *= -1.1
            ball_vel[1] *= 1.1
        else:
            score[1] += 1
            ball_init(False)

    if ball_pos[0] >= WIDTH - BALL_RADIUS - PAD_WIDTH:
        if paddle2_pos < ball_pos[1] < paddle2_pos + PAD_HEIGHT:
            ball_vel[0] *= -1.1
            ball_vel[1] *= 1.1

        else:
            score[0] += 1
            ball_init(True)



# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button("Restart", new_game, 150)

timer = simplegui.create_timer(50, tick)

new_game()

# start frame
frame.start()
timer.start()
