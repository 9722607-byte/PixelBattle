package pixelbattle.heroes;

import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;
import pixelbattle.core.Fighter;
import pixelbattle.core.Ultimate;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));

        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int dmg1 = target.takeDamage(random(7, 11));
            int dmg2 = target.takeDamage(random(7, 11));
            int totalDmg = dmg1 + dmg2;
            return user.getName() + " робить подвійний постріл у " + target.getName() + " на суму: " + totalDmg + " шкоди";
        }));

        setUltimate(new Ultimate() {
            private int arrowCount = 3;

            @Override
            public String getName() {
                return "Злива стріл";
            }

            @Override
            public String getDescription() {
                return "Випускає град стріл по ворогах";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                for (Fighter e : enemies) {
                    if (e.isAlive()) {
                        for (int i = 0; i < arrowCount; i++) {
                            e.takeDamage(Fighter.random(4, 8));
                        }
                    }
                }
                int current = arrowCount;
                arrowCount++;
                return user.getName() + " влаштовує зливу стріл (" + current + " стріл на ворога)!";
            }
        });
    }

    @Override
    protected int modifyIncomingDamage(int damage) {
        if (chance(25)) {
            return 0;
        }
        return damage;
    }
}
