defmodule Aoc24.Day02 do
  @spec part1(String.t()) :: integer()
  def part1(file \\ "./assets/day01/input.txt") do
    file
    |> parse_input()
    |> Enum.filter(&is_safe/1)
    |> Enum.count()
  end

  defp is_safe([a, b | _rest] = numbers) do
    f = if(a > b, do: &Kernel.>/2, else: &Kernel.</2)

    numbers
    |> Enum.chunk_every(2, 1)
    |> Enum.reduce(true, fn
      [_], acc -> acc
      [a, b], acc -> f.(a, b) and abs(a - b) >= 1 and abs(a - b) <= 3 and acc
    end)
  end

  @spec part2(String.t()) :: integer()
  def part2(file \\ "./assets/day01/input.txt") do
    file
    |> parse_input()
    |> Enum.filter(&is_safe_with_margin/1)
    |> Enum.count()
  end

  defp parse_input(file) do
    file
    |> File.read!()
    |> String.split("\n")
    |> Enum.map(&String.split/1)
    |> Enum.map(fn numbers -> Enum.map(numbers, &String.to_integer/1) end)
    |> Enum.reject(&Enum.empty?/1)
  end

  defp is_safe_with_margin(numbers) do
    is_safe(numbers) or is_safe_without_one(numbers)
  end

  defp is_safe_without_one(numbers) do
    len = length(numbers)

    Enum.any?(0..(len - 1), &is_safe(List.delete_at(numbers, &1)))
  end
end
