defmodule Aoc24 do
  def to_matrix(text, row_split \\ &String.split(&1, "\n"), col_split \\ &String.codepoints/1) do
    text
    |> row_split.()
    |> Enum.with_index()
    |> Enum.flat_map(fn {row, row_idx} ->
      row
      |> col_split.()
      |> Enum.with_index()
      |> Enum.map(fn {itm, col_idx} -> {{col_idx, row_idx}, itm} end)
    end)
    |> Map.new()
  end

  def diagonals({x, y}, len \\ 1) do
    [
      Enum.map(0..len, &{x + &1, y + &1}),
      Enum.map(0..len, &{x + &1, y - &1}),
      Enum.map(0..len, &{x - &1, y - &1}),
      Enum.map(0..len, &{x - &1, y + &1})
    ]
  end

  def cross({x, y}, len \\ 1) do
    [
      Enum.map(0..len, &{x + &1, y}),
      Enum.map(0..len, &{x - &1, y}),
      Enum.map(0..len, &{x, y - &1}),
      Enum.map(0..len, &{x, y + &1})
    ]
  end
end
