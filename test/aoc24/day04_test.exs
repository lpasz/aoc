defmodule Aoc24.Day04Test do
  use ExUnit.Case, async: true

  alias Aoc24.Day04

  describe "part1" do
    test "example" do
      assert 18 == Day04.part1("./assets/day04/example.txt")
    end

    test "input" do
      assert 2427 == Day04.part1("./assets/day04/input.txt")
    end
  end

  describe "part2" do
    test "example" do
      assert 9 == Day04.part2("./assets/day04/example.txt")
    end

    test "input" do
      assert 1900 == Day04.part2("./assets/day04/input.txt")
    end
  end
end
