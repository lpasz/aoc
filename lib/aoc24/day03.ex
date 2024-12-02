defmodule Aoc24.Day03 do
  @spec part1(String.t()) :: integer()
  def part1(file \\ "./assets/day01/input.txt") do
    file
    |> parse_input()
  end

  @spec part2(String.t()) :: integer()
  def part2(file \\ "./assets/day01/input.txt") do
    file
    |> parse_input()
  end

  defp parse_input(file) do
    file
    |> File.read!()
  end
end
