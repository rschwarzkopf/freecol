/**
 *  Copyright (C) 2002-2016   The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import net.sf.freecol.common.io.FreeColXMLReader;
import net.sf.freecol.common.io.FreeColXMLWriter;
import static net.sf.freecol.common.util.CollectionUtils.*;


/**
 * The effect of a natural disaster or other event. How the
 * probability of the effect is interpreted depends on the number of
 * effects value of the disaster or event. If the number of effects is
 * ALL, the probability is ignored. If it is ONE, then the probability
 * may be an arbitrary integer, and is used only for comparison with
 * other effects. If the number of effects is SEVERAL, however, the
 * probability must be a percentage.
 *
 * @see Disaster
 */
public class Effect extends FreeColSpecObjectType {

    public static final String DAMAGED_UNIT
        = "model.disaster.effect.damagedUnit";
    public static final String LOSS_OF_UNIT
        = "model.disaster.effect.lossOfUnit";
    public static final String LOSS_OF_MONEY
        = "model.disaster.effect.lossOfMoney";
    public static final String LOSS_OF_GOODS
        = "model.disaster.effect.lossOfGoods";
    public static final String LOSS_OF_TILE_PRODUCTION
        = "model.disaster.effect.lossOfTileProduction";
    public static final String LOSS_OF_BUILDING
        = "model.disaster.effect.lossOfBuilding";
    public static final String LOSS_OF_BUILDING_PRODUCTION
        = "model.disaster.effect.lossOfBuildingProduction";

    /** The probability of this effect. */
    private int probability;


    /**
     * Deliberately empty constructor.
     */
    protected Effect() {}

    /**
     * Creates a new <code>Effect</code> instance.
     *
     * @param xr The <code>FreeColXMLReader</code> to read from.
     * @param specification The <code>Specification</code> to refer to.
     * @exception XMLStreamException if an error occurs
     */
    public Effect(FreeColXMLReader xr, Specification specification) throws XMLStreamException {
        setSpecification(specification);
        readFromXML(xr);
    }

    /**
     * Create a new effect from an existing one.
     *
     * @param template The <code>Effect</code> to copy from.
     */
    public Effect(Effect template) {
        setId(template.getId());
        setSpecification(template.getSpecification());
        this.probability = template.probability;
        setScopes(template.getScopeList());
        addFeatures(template);
    }


    /**
     * Get the probability of this effect.
     *
     * @return The probability.
     */
    public final int getProbability() {
        return probability;
    }


    // Serialization

    private static final String PROBABILITY_TAG = "probability";


    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeAttributes(FreeColXMLWriter xw) throws XMLStreamException {
        super.writeAttributes(xw);

        xw.writeAttribute(PROBABILITY_TAG, probability);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readAttributes(FreeColXMLReader xr) throws XMLStreamException {
        super.readAttributes(xr);

        probability = xr.getAttribute(PROBABILITY_TAG, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('[').append(getId())
            .append(" probability=").append(probability).append('%');
        for (Scope scope : getScopeList()) sb.append(' ').append(scope);
        sb.append(']');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXMLTagName() { return getTagName(); }

    /**
     * Gets the XML tag name for this element.
     *
     * @return "effect".
     */
    public static String getTagName() {
        return "effect";
    }
}
