defmodule Aoc24.Day03 do
  @spec part1(String.t()) :: integer()
  def part1(file \\ "./assets/day01/input.txt") do
    text = parse_input(file)

    ~r/mul\(\d{1,3},\d{1,3}\)/
    |> Regex.scan(text)
    |> Enum.map(&List.first/1)
    |> Enum.map(&Regex.scan(~r/\d+/, &1))
    |> Enum.map(fn [[n1], [n2]] -> String.to_integer(n1) * String.to_integer(n2) end)
    |> Enum.sum()
  end

  @spec part2(String.t()) :: integer()
  def part2(file \\ "./assets/day01/input.txt") do
    text =
      file
      |> parse_input()

    ~r/(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))/
    |> Regex.scan(text)
    |> Enum.map(&List.first/1)
    |> Enum.map(fn
      "do()" -> :enable
      "don't()" -> :disable
      mul -> Regex.scan(~r/\d+/, mul)
    end)
    |> Enum.reduce([0, true], fn
      :enable, [cnt, enabled?] -> [cnt, true]
      :disable, [cnt, enabled?] -> [cnt, false]
      [[n1], [n2]], [cnt, true] -> [cnt + String.to_integer(n1) * String.to_integer(n2), true]
      _, [cnt, false] -> [cnt, false]
    end)
    |> List.first()
  end

  defp parse_input(file) do
    file
    |> File.read!()
  end
end
