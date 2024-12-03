defmodule Aoc24.Day03 do
  def part1(file \\ "./assets/day01/input.txt") do
    text = File.read!(file)

    ~r/mul\(\d{1,3},\d{1,3}\)/
    |> Regex.scan(text)
    |> Enum.map(&List.first/1)
    |> Enum.map(&mul/1)
    |> Enum.sum()
  end

  def part2(file \\ "./assets/day01/input.txt") do
    text = File.read!(file)

    ~r/(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))/
    |> Regex.scan(text)
    |> Enum.map(&List.first/1)
    |> Enum.map(fn
      "do()" -> {:ignore, false}
      "don't()" -> {:ignore, true}
      expr -> {:mul, mul(expr)}
    end)
    |> Enum.reduce({0, false}, fn
      {:mul, mul}, {cnt, _ignored? = false} -> {cnt + mul, false}
      {:mul, _ignored_mul}, {cnt, _ignored? = true} -> {cnt, true}
      {:ignore, ignore?}, {cnt, _ignore?} -> {cnt, ignore?}
    end)
    |> then(fn {cnt, _} -> cnt end)
  end

  defp mul(expr) do
    [[n1], [n2]] = Regex.scan(~r/\d+/, expr)
    String.to_integer(n1) * String.to_integer(n2)
  end
end
