(ns aoc23.day24
  "Never Tell Me The Odds"
  (:require [clojure.string :as s]))

(defn in-range [s f [x y]]
  (and (<= s x f) (<= s y f)))

(def part1-rng (partial in-range 200000000000000 400000000000000))
(def exp1-rng (partial in-range 7 27))

(defn after?
  "Returns a function that given x-y coord, will return true if that coord 
   happens after the line stating point"
  [pt-or-number]
  (if (number? pt-or-number)
    ({-1 < 1 >} (compare pt-or-number 0))
    (let [[x y _z vx vy _vz] pt-or-number]
      (fn [[nx ny]]
        (and ((after? vx) nx x) ((after? vy) ny y))))))

(defn next-point
  "Given a starting point and velocity returns the next point"
  [[x y vx vy]]
  [(+ x vx) (+ y vy)])

(defn compute-line
  " If lines are not parallel return the slope and the offset of the line, this is the line equation"
  [[x1 y1] [x2 y2]]
  (when-not (zero? (- x2 x1))
    (let [m (/ (- y2 y1)
               (- x2 x1))]
      {:slope  m
       :offset (- y1 (* m x1))})))

(defn parse-raw [inp]
  (->> (re-seq #"(?m)(.*)\,(.*)\,(.*)\@(.*)\,(.*)\,(.*)" inp)
       (mapcat rest)
       (map s/trim)
       (map  parse-long)
       (partition 6)))

(defn parse [inp]
  (->> (parse-raw inp)
       (map (fn [[x y z vx vy vz]]
              {:pt1 [x y]
               :pt2 (next-point [x y vx vy])
               :after? (after? [x y z vx vy vz])}))
       (map (fn [{:keys [pt1 pt2] :as m}]
              (merge m (compute-line pt1 pt2))))))

(defn intersect [line1 line2]
  (let [div (- (:slope  line2) (:slope  line1))]
    (if (zero? div)
      nil
      (let [x (/ (- (:offset line1) (:offset line2)) div)
            y (+ (* (:slope line1) x)
                 (:offset line1))]
        [x y]))))

(defn combinations
  "Give all the pair combinations of hailstones.
   [1 2 3 4] => ([4 3] [1 4] [4 2] [1 3] [1 2] [3 2])"
  [coll]
  (->> (for [h1 coll
             h2 coll
             :when (not= h1 h2)]
         #{h1 h2})
       (into #{})
       (map vec)))

(defn update-velocity [[x y vx vy] vel1 vel2]
  [x y (+ vx vel1) (+ vy vel2)])

(defn to-line [hailstone vx vy]
  (let [hailstone (update-velocity hailstone vx vy)
        next-hailstone (next-point hailstone)]
    (compute-line hailstone next-hailstone)))

(defn all-hailstones-colide-at
  "Given the new velocity on x and y axis (or x and z, it does not matter) 
   check if all hailstones converge to the same point. 
   (which is the starting point of the rock).
   
   If they converge to the same point, them the anwser on xy or xz axis is the 
   point it converge and the inverse of the vx vy vz velocities"
  [hailstones [vx vy]]
  (loop [hailstones hailstones
         colide-at nil
         ;; this is just so we can speed up, 
         ;; so if 5 converge in the same point
         ;; we stop and call it the day
         speed-up 0]
    (if (or (>= 1 (count hailstones))
            (= speed-up 5))
      colide-at
      (let [h1-line (to-line (first hailstones) vx vy)
            h2-line (to-line (second hailstones) vx vy)
            hrest (rest (rest hailstones))]
        (when (and h1-line h2-line)
          (when-let [intersect (intersect h1-line h2-line)]
            (let [intersect-at (conj intersect vx vy)
                  speed-up (inc speed-up)]
              (cond (nil? colide-at) (recur hrest intersect-at speed-up)
                    (= intersect-at colide-at) (recur hrest intersect-at  speed-up)))))))))

(defn velocities []
  (for [vx (range -300 200)
        vy (range -300 200)
        :when (or (not (zero? vx))
                  (not (zero? vy)))]
    [vx vy]))

(defn relative-rock-finding
  "Changes hailstones velocity and check if all hailstones colide in the same point"
  [hailstones]
  (->> (velocities)
       (keep #(all-hailstones-colide-at hailstones %))
       (first)))

(defn raw->x-y [[x y _z vx vy _vz]] [x y vx vy])
(defn raw->x-z [[x _y z vx _vy vz]] [x z vx vz])

(defn part1
  "Compare every line to each other, if line intersects and that 
   intersection happen after the starting point of both lines and inside the predetermine zone we count that."
  [input rng]
  (->> (parse input)
       (combinations)
       (keep (fn [[line1 line2]]
               (when-let [intersect (intersect line1 line2)]
                 (when (and ((:after? line1) intersect) ((:after? line2) intersect))
                   intersect))))
       (filter rng)
       (count)))

(defn part2
  "Uses a technique that presumes that the rock is a static point, and changes the velocities of the hailstones
   from the lower to upper bounds of available velocities. 
   If all hailstones converge to a single point in a given updated velocity
   we know that that point and the inverse of that velocity is what would be the starting position/velocity of 
   the rock that would crash all the hailstones eventually"
  [inp]
  (let [raw (parse-raw inp)
        raw-xy (map raw->x-y raw)
        raw-xz (map raw->x-z raw)
        [x y _ _] (relative-rock-finding raw-xy)
        [_ z _ _] (relative-rock-finding raw-xz)]
    (+ x y z)))


