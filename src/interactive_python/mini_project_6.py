__author__ = 'vvlasov'

# Mini-project #6 - Blackjack

import simplegui
import random


CANVAS_SIZE = 600
# load card sprite - 949x392 - source: jfitz.com
CARD_SPACING = 4
CARD_SIZE = (73, 98)
CARD_CENTER = (36.5, 49)
card_images = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/cards.jfitz.png")

CARD_BACK_SIZE = (71, 96)
CARD_BACK_CENTER = (35.5, 48)
card_back = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/card_back.png")

# initialize some useful global variables
in_play = False
outcome = ""
score = 0

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9, 'T': 10, 'J': 10, 'Q': 10, 'K': 10}

# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def get_value(self):
        return VALUES[self.get_rank()]

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank),
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]],
                          CARD_SIZE)


# define hand class
class Hand:
    def __init__(self, dealer):
        self.cards = []
        self.dealer = dealer

    def __str__(self):
        return str(self.cards)

    def add_card(self, card):
        self.cards.append(card)

    def get_value(self):
        return sum([card.get_value() for card in self.cards])

    def draw(self, canvas, pos):
        # x coord is being calculated dynamically
        x = (CANVAS_SIZE - len(self.cards) * CARD_SIZE[0] + (len(self.cards) - 1) * CARD_SPACING) / 2

        index = 0
        for card in self.cards:
            if index == 0 and self.dealer and in_play:
                self.draw_hidden_card(canvas, (x + index * (CARD_SIZE[0] + CARD_SPACING), pos[1]))
            else:
                card.draw(canvas, (x + index * (CARD_SIZE[0] + CARD_SPACING), pos[1]))
            index += 1

    def draw_hidden_card(self, canvas, pos):
        card_loc = (card_back.get_width() / 2, card_back.get_height() / 2)
        canvas.draw_image(card_back, card_loc, (card_back.get_width(), card_back.get_height()),
                          [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]],
                          CARD_SIZE)


# define deck class
class Deck:
    def __init__(self):
        self.cards = []

        for suit in SUITS:
            for rank in RANKS:
                self.cards.append(Card(suit, rank))

    def shuffle(self):
        self.shuffled = list(self.cards)
        random.shuffle(self.shuffled)

    def deal_card(self):
        return self.shuffled.pop()

    def __str__(self):
        return str(self.shuffled)


#define event handlers for buttons
deck = Deck()
dealer_hand = Hand(True)
player_hand = Hand(False)


def deal():
    global outcome, score, in_play, player_hand, dealer_hand

    # your code goes here
    if in_play:
        outcome = "Player lost, hit or stand?"
        score -= 1
    else:
        outcome = "Hit or stand?"

    in_play = True
    player_hand = Hand(False)
    dealer_hand = Hand(True)
    deck.shuffle()

    for i in range(2):
        player_hand.add_card(deck.deal_card())
        dealer_hand.add_card(deck.deal_card())


def hit():
    global outcome, score, in_play

    if not in_play:
        return

    player_hand.add_card(deck.deal_card())

    if player_hand.get_value() > 21:
        # busted
        outcome = "Busted, dealer wins! New deal?"
        score -= 1
        in_play = False
        # if the hand is in play, hit the player

        # if busted, assign a message to outcome, update in_play and score


def stand():
    global outcome, score, in_play

    if not in_play:
        return

    while dealer_hand.get_value() < 17:
        dealer_hand.add_card(deck.deal_card())

    if dealer_hand.get_value() > 21 or dealer_hand.get_value() < player_hand.get_value():
        outcome = "Player wins, new deal? "
        score += 1
    else:
        outcome = "Dealer wins, new deal?"
        score -= 1

    in_play = False

# draw handler
def draw(canvas):
    canvas.draw_text("Dealer", (270, 100), 24, "black")
    canvas.draw_text("Player", (280, 500), 24, "black")
    dealer_hand.draw(canvas, [300, 130])
    player_hand.draw(canvas, [300, 370])

    canvas.draw_text("Blackjack", (230, 300), 40, "white")
    canvas.draw_text("Score: " + str(score), (20, 20), 20, "white")
    canvas.draw_text(str(outcome), (20, 45), 18, "white")



# initialization frame
frame = simplegui.create_frame("Blackjack", CANVAS_SIZE, CANVAS_SIZE)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit", hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling
frame.start()
deal()

# remember to review the gradic rubric
