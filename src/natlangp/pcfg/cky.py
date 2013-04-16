from collections import defaultdict
import json

__author__ = 'vvlasov'


def cky(sentence, terminal, rules, q_score):
    result = defaultdict(dict)

    for l in xrange(len(sentence)):
        word = sentence[l] if rules.get(sentence[l], None) is not None else "_RARE_"
        applicable_rules = rules[word]

        for rule in applicable_rules:
            result[(l, l)][rule] = (q_score(word, rule), (word, '-'), l)

    for l in xrange(0, len(sentence)):
        for i in xrange(len(sentence) - l + 1):
            j = l + i
            for s in range(i, j):
                prods1 = result[(i, s)]
                prods2 = result[(s + 1, j)]

                for prod1 in prods1:
                    for prod2 in prods2:
                        applicable_rules = rules.get((prod1, prod2), {})
                        for rule in applicable_rules:
                            current_score = result[i, j].get(rule, (-1, ()))[0]
                            candidate_score = q_score((prod1, prod2), rule) * prods1[prod1][0] * prods2[prod2][0]
                            if current_score < candidate_score:
                                result[i, j][rule] = (candidate_score, (prod1, prod2), s)

    return collect(result, terminal, 0, len(sentence) - 1)


def collect(result, terminal, start, end):
    next_terminal = result[(start, end)][terminal]

    if start == end:
        return [terminal, next_terminal[1][0]]

    return [terminal, collect(result, next_terminal[1][0], start, next_terminal[2]),
            collect(result, next_terminal[1][1], next_terminal[2] + 1, end)]


def prepare(file_name):
    with open(file_name, 'rb') as in_file:
        lines = [line.strip() for line in in_file.readlines()]
        bottom_up_rules = defaultdict(dict)
        prod_counter = defaultdict(int)

        for line in lines:
            line_split = line.split(" ")
            if line_split[1] == "UNARYRULE":
                bottom_up_rules[line_split[3]][line_split[2]] = int(line_split[0])
                prod_counter[line_split[2]] += int(line_split[0])
            elif line_split[1] == "BINARYRULE":
                bottom_up_rules[(line_split[3], line_split[4])][line_split[2]] = int(line_split[0])
                prod_counter[line_split[2]] += int(line_split[0])

        def q_score(in_rules, out_rule):
            ruleScore = float(bottom_up_rules[in_rules].get(out_rule, 0))
            return ruleScore / prod_counter[out_rule] if ruleScore > 0 else 0

        return bottom_up_rules, q_score


if __name__ == '__main__':
    prepared = prepare('h2/parse_train.counts.out')
    with open('h2/parse_test.dat', 'rb') as file_in, open('h2/parse_test.p2.out', 'wb') as file_out:
        sentences = [line.strip() for line in file_in.readlines()]

        for sentence in sentences:
            file_out.write(json.dumps(cky(sentence.split(' '), 'SBARQ', prepared[0], prepared[1])))
            file_out.write(u'\n')

        file_out.close()
        file_in.close()