package com.hr.note.pattern.lol.model.team;

import com.hr.note.pattern.lol.model.hero.ViktorHero;
import com.hr.note.pattern.lol.model.skill.FlashSS;
import com.hr.note.pattern.lol.model.skill.IgniteSS;

/**
 * Created by hurui on 2017/6/28.
 */
public class SamsungEdgTeam extends Team {

  private Team team;

  public SamsungEdgTeam(Team team) {
    this.team = team;
  }

  @Override
  public void adjustStrategy() throws Exception {
    team.adjustStrategy();
    team.strategy.setMid(new ViktorHero(new FlashSS(), new IgniteSS()));
  }

  @Override
  public void executeStrategy() {
    team.strategy.printStrategy();
  }
}
