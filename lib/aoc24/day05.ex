defmodule Aoc24.Day05 do
  def part1(file) do
    {rules, pages} = rules_and_pages(file)

    pages
    |> Enum.filter(&(&1 == ordered_page(&1, rules)))
    |> Enum.map(&middle/1)
    |> Enum.sum()
  end

  def part2(file) do
    {rules, pages} = rules_and_pages(file)

    pages
    |> Enum.map(&ordered_page(&1, rules))
    |> Enum.zip(pages)
    |> Enum.filter(fn {ordered, unordered} -> ordered != unordered end)
    |> Enum.map(fn {ordered, _} -> ordered end)
    |> Enum.map(&middle/1)
    |> Enum.sum()
  end

  defp middle(coll) do
    middle = coll |> length() |> div(2)

    Enum.at(coll, middle)
  end

  defp ordered_page(page, rules) do
    Enum.sort_by(page, fn itm ->
      before = Map.get(rules, itm, [])

      Enum.count(page, &(&1 in before))
    end)
  end

  def rules_and_pages(file) do
    [rules, pages] =
      file
      |> File.read!()
      |> String.split("\n\n")

    {rules(rules), pages(pages)}
  end

  defp rules(rules) do
    rules
    |> String.trim()
    |> String.split("\n")
    |> Enum.map(&String.split(&1, "|"))
    |> Enum.map(&Enum.reverse/1)
    |> Enum.map(fn nums -> Enum.map(nums, &String.to_integer/1) end)
    |> Enum.group_by(&List.first/1)
    |> Enum.map(fn {key, nums} -> {key, Enum.map(nums, fn [_, i] -> i end)} end)
    |> Map.new()
  end

  defp pages(pages) do
    pages
    |> String.trim()
    |> String.split("\n")
    |> Enum.map(&String.split(&1, ","))
    |> Enum.map(fn nums -> Enum.map(nums, &String.to_integer/1) end)
  end
end
