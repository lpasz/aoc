defmodule Aoc24.Day05Test do
  use ExUnit.Case, async: true

  alias Aoc24.Day05

  describe "part1" do
    test "example" do
      assert Day05.part1("./assets/day05/example.txt") == 143
    end

    test "input" do
      assert Day05.part1("./assets/day05/input.txt") == 7365
    end
  end

  describe "part2" do
    test "example" do
      assert Day05.part2("./assets/day05/example.txt") == 123
    end

    test "input" do
      assert Day05.part2("./assets/day05/input.txt") == 5770
    end
  end
end
