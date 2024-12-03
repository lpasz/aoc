defmodule Aoc24.Day01 do
  def part1(file) do
    file
    |> parse_input()
    |> Enum.map(&Enum.sort/1)
    |> then(fn [a, b] -> Enum.zip(a, b) end)
    |> Enum.map(fn {a, b} -> abs(a - b) end)
    |> Enum.sum()
  end

  def part2(file) do
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
    |> Enum.map(fn nums -> Enum.map(nums, &String.to_integer/1) end)
  end
end
