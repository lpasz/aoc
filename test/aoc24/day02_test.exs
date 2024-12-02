defmodule Aoc24.Day02Test do
  use ExUnit.Case, async: true

  alias Aoc24.Day02

  describe "part1" do
    test "example" do
      assert 2 == Day02.part1("./assets/day02/example.txt")
    end

    test "part1" do
      assert 670 == Day02.part1("./assets/day02/input.txt")
    end
  end

  describe "part2" do
    test "example" do
      assert 4 == Day02.part2("./assets/day02/example.txt")
    end

    test "part2" do
      assert 700 == Day02.part2("./assets/day02/input.txt")
    end
  end
end
