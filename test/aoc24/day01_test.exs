defmodule Aoc24.Day01Test do
  use ExUnit.Case, async: true

  alias Aoc24.Day01

  describe "part 1" do
    test "example 1" do
      assert 11 == Day01.part1("./assets/day01.example.txt")
    end

    test "part 1" do
      assert 2_367_773 == Day01.part1("./assets/day01.txt")
    end
  end

  describe "part 2" do
    test "example 2" do
      assert 31 == Day01.part2("./assets/day01.example.txt")
    end

    test "part 2" do
      assert 21_271_939 == Day01.part2("./assets/day01.txt")
    end
  end
end
