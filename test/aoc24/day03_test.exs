defmodule Aoc24.Day03Test do
  use ExUnit.Case, async: true

  alias Aoc24.Day03

  describe "part1" do
    test "example" do
      assert 0 == Day03.part1("./assets/day03/example.txt")
    end

    test "input" do
      assert 0 == Day03.part1("./assets/day03/input.txt")
    end
  end

  describe "part2" do
    test "example" do
      assert 0 == Day03.part2("./assets/day03/example.txt")
    end

    test "input" do
      assert 0 == Day03.part2("./assets/day03/input.txt")
    end
  end
end
