package nexo.dual_axe.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.WeaponCategory;


public enum BetterAxeWeaponCategories implements WeaponCategory {

    DA_AXE;

    final int id;

    BetterAxeWeaponCategories(){this.id = WeaponCategory.ENUM_MANAGER.assign(this);}

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
