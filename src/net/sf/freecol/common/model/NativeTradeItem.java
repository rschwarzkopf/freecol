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

import javax.xml.stream.XMLStreamException;

import net.sf.freecol.common.i18n.Messages;
import net.sf.freecol.common.io.FreeColXMLReader;
import net.sf.freecol.common.io.FreeColXMLWriter;
import net.sf.freecol.common.util.Utils;


/**
 * A trade item consisting of some goods to be traded with the natives.
 * Therefore we include the current valuation and the haggle count.
 */
public class NativeTradeItem extends GoodsTradeItem {

    /** Magic number to denote that the price has not been initialized. */
    public static final int PRICE_UNSET = 0;
    
    /** The current valuation by the natives. */
    private int price;

    /** The number of haggling rounds. */
    private int haggleCount;
    

    /**
     * Creates a new <code>NativeTradeItem</code> instance.
     *
     * @param game The enclosing <code>Game</code>.
     * @param source The source <code>Player</code>.
     * @param destination The destination <code>Player</code>.
     * @param goods The <code>Goods</code> to trade.
     */
    public NativeTradeItem(Game game, Player source, Player destination,
                           Goods goods) {
        super(game, source, destination, goods);

        this.price = PRICE_UNSET;
        this.haggleCount = 0;
    }

    /**
     * Copy a new <code>NativeTradeItem</code> instance.  Used by readList.
     *
     * @param nti The <code>NativeTradeItem</code> to copy.
     */
    public NativeTradeItem(NativeTradeItem nti) {
        super(nti.getGoods().getGame(), nti.getSource(), nti.getDestination(),
              nti.getGoods());

        this.price = nti.getPrice();
        this.haggleCount = nti.getHaggleCount();
    }

    /**
     * Creates a new <code>NativeTradeItem</code> instance.
     *
     * @param game The enclosing <code>Game</code>.
     * @param xr The <code>FreeColXMLReader</code> to read from.
     * @exception XMLStreamException if there is an error reading the stream.
     */
    public NativeTradeItem(Game game, FreeColXMLReader xr) throws XMLStreamException {
        super(game, xr);
    }


    /**
     * Has a price been set for this item?
     *
     * @param True if a price has been set.
     */
    public boolean priceIsSet() {
        return this.price != PRICE_UNSET;
    }

    /**
     * Get the price for this item.
     *
     * @return The current price.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Set the price for this item.
     *
     * @param price The new price.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Get the haggle count for this item.
     *
     * @return The current haggle count.
     */
    public int getHaggleCount() {
        return this.haggleCount;
    }

    /**
     * Set the haggle count for this item.
     *
     * @param haggleCount The new haggle count.
     */
    public void setHaggleCount(int haggleCount) {
        this.haggleCount = haggleCount;
    }


    // Interface TradeItem
    // All provided by superclass

    // Override Object

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof NativeTradeItem) {
            return this.price == ((NativeTradeItem)other).price
                && this.haggleCount == ((NativeTradeItem)other).haggleCount
                && super.equals((NativeTradeItem)other);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 37 * hash + this.price;
        return 37 * hash + this.haggleCount;
    }


    // Serialization

    private static final String HAGGLE_COUNT_TAG = "haggleCount";
    private static final String PRICE_TAG = "price";


    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeAttributes(FreeColXMLWriter xw) throws XMLStreamException {
        super.writeAttributes(xw);

        xw.writeAttribute(PRICE_TAG, this.price);

        xw.writeAttribute(HAGGLE_COUNT_TAG, this.haggleCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readAttributes(FreeColXMLReader xr) throws XMLStreamException {
        super.readAttributes(xr);

        this.price = xr.getAttribute(PRICE_TAG, -1);

        this.haggleCount = xr.getAttribute(HAGGLE_COUNT_TAG, -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        sb.append('[').append(getId())
            .append(' ').append(this.goods.getAmount()).append(' ')
            .append(Messages.getName(this.goods))
            .append(' ').append(price).append(' ').append(haggleCount)
            .append(']');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXMLTagName() { return getTagName(); }

    /**
     * Gets the tag name of the root element representing this object.
     *
     * @return "nativeTradeItem".
     */
    public static String getTagName() {
        return "nativeTradeItem";
    }
}
