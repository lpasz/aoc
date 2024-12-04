defmodule Aoc24.Day04 do
  def part1(file) do
    file = File.read!(file)
    mtx = Aoc24.to_matrix(file)

    mtx
    |> Map.keys()
    |> Enum.flat_map(fn coord ->
      coord
      |> xmas_diagonals()
      |> Enum.map(&to_coord_xmas(&1, mtx))
    end)
    |> Enum.filter(&(&1 in ["XMAS", "SAMX"]))
    |> Enum.count()
    |> div(2)
  end

  def part2(file) do
    file = File.read!(file)
    mtx = Aoc24.to_matrix(file)

    mtx
    |> Enum.filter(fn {_coord, letter} -> letter == "A" end)
    |> Enum.map(fn {coord, "A"} ->
      coord
      |> x_mas()
      |> to_coord_xmas(mtx)
    end)
    |> Enum.filter(&(&1 in ["MMSS", "SMMS", "SSMM", "MSSM"]))
    |> Enum.count()
  end

  defp to_coord_xmas(coords, mtx) do
    coords
    |> Enum.map(&Map.get(mtx, &1))
    |> Enum.reduce("", &(&2 <> to_string(&1)))
  end

  defp x_mas(coord) do
    coord
    |> Aoc24.diagonals()
    |> Enum.map(&Enum.drop(&1, 1))
    |> Enum.map(&List.first/1)
  end

  defp xmas_diagonals(coord) do
    Aoc24.cross(coord, 3) ++ Aoc24.diagonals(coord, 3)
  end
end
