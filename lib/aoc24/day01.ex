defmodule Aoc24.Day01 do
  @spec part1(String.t()) :: integer()
  def part1(file \\ "./assets/day01.txt") do
    file
    |> parse_input()
    |> then(fn [a, b] -> Enum.zip(Enum.sort(a), Enum.sort(b)) end)
    |> Enum.map(fn {a, b} -> abs(a - b) end)
    |> Enum.sum()
  end

  @spec part2(String.t()) :: integer()
  def part2(file \\ "./assets/day01.txt") do
    file
    |> parse_input()
    |> then(fn [left, right] ->
      right = Enum.frequencies(right)

      left
      |> Enum.map(&(&1 * Map.get(right, &1, 0)))
      |> Enum.sum()
    end)
  end

  defp parse_input(file) do
    file
    |> File.read!()
    |> String.split("\n")
    |> Enum.reject(&(&1 == ""))
    |> Enum.map(&String.split(&1, "   "))
    |> Enum.zip_with(& &1)
    |> then(fn [a, b] ->
      left = Enum.map(a, &String.to_integer/1)
      right = Enum.map(b, &String.to_integer/1)
      [left, right]
    end)
  end
end
