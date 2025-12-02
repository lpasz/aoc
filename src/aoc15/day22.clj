(ns aoc15.day22
  (:require [core :as c]))

(def boss {:hp 58 :dmg 9})
(def me {:hp 50 :mana 500 :consumed-mana 0 :armor 0})

(def magics {:magic-missile {:name :magic-missile
                             :costs 53
                             :dmg 4}
             :drain {:name :drain
                     :costs 73
                     :dmg 2
                     :hp 2}
             :shield {:name :shield
                      :costs 113
                      :armor 7
                      :effect {:name :shield
                               :lasts 6
                               :type :on-expiry
                               :armor -7}}
             :poison {:name :poison
                      :costs 173
                      :effect {:name :poison
                               :lasts 6
                               :type :every-turn
                               :dmg 3}}
             :recharge {:name :recharge
                        :costs 229
                        :effect {:name :recharge
                                 :lasts 5
                                 :type :every-turn
                                 :mana 101}}})

(defn round [{:keys [me boss effects] :as world}])

(defn player-turn [{:keys [me boss] :as world} magic]
  (c/insp me)
  (case (:name magic)

    :magic-missile {:me (-> me
                            (update :consumed-mana #(+ % (:costs magic)))
                            (update :mana #(- % (:costs magic))))

                    :boss (update boss :hp #(- % (:dmg magic)))}

    :drain {:me (-> me
                    (update :consumed-mana #(+ % (:costs magic)))
                    (update :mana          #(- % (:costs magic)))
                    (update :hp            #(+ % (:hp magic))))

            :boss (update boss :hp #(- % (:dmg magic)))
            :effect nil}

    :shield {:me (-> me
                     (update :consumed-mana #(+ % (:costs magic)))
                     (update :mana #(- % (:costs magic)))
                     (update :armor #(+ % (:armor magic))))
             :boss boss
             :effect  (:effect magic)}

    :poison {:me (-> me
                     (c/insp)
                     (update :consumed-mana #(+ % (:costs magic)))
                     (update :mana #(- % (:costs magic))))
             :boss boss
             :effect (:effect magic)}

    :recharge {:me (-> me
                       (update :consumed-mana #(+ % (:costs magic)))
                       (update :mana #(- % (:costs magic))))
               :boss boss
               :effect (:effects magic)}))

(defn apply-effects [{:keys [me boss effects]}]
  (reduce (fn [{:keys [me boss effects]} effect]
            (case (:name effect)

              :shield (if (zero? (dec (:lasts effect)))
                        {:me (update me :armor #(+ % (:armor effect)))
                         :boss boss
                         :effects effects}
                        {:me me
                         :boss boss
                         :effects (conj effects (update effect :lasts dec))})
              :poison {:me me
                       :boss (update boss :hp #(- % (:dmg effect)))
                       :effects (if (zero? (dec (:lasts effect)))
                                  effects (conj effects effect))}

              :recharge {:me (update me :mana #(+ % (:mana effect)))
                         :boss boss
                         :effects (if (zero? (dec (:lasts effect)))
                                    effects (conj effects effect))}))
          {:me me :boss boss :effects []}
          effects))

(defn results [me boss]
  (cond (< (:mana me) 0) :lost
        (< (:hp boss) 0) :won
        :else nil))

(defn player-round [{:keys [effects] :as world} magic]
  (let [{:keys [me boss effect]} (player-turn world magic)]
    (if-let [r (results me boss)]
      {:result r :world {:me me :boss boss :effects (conj effect effects)}}
      (let [{:keys [me boss effects]} (apply-effects {:me me :boss boss :effects effects})
            world {:me me :boss boss :effects (conj effects effect)}]
        (if-let [r (results me boss)]
          {:result r :world world}
          world)))))


