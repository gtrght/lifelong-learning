def collatz(n):
    result = []

    while n is not 1:
        result.append(n)
        if n % 2 == 1:
            n *= 3
            n += 1
        else:
            n /= 2

    result.append(1)

    return result

print max(collatz(23))
print max(collatz(217))

