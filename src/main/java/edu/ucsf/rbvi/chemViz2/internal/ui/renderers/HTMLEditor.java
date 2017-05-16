/*
  Copyright (c) 2006, 2007, 2008 The Cytoscape Consortium (www.cytoscape.org)

  The Cytoscape Consortium is:
  - Institute for Systems Biology
  - University of California San Diego
  - Memorial Sloan-Kettering Cancer Center
  - Institut Pasteur
  - Agilent Technologies

  This library is free software; you can redistribute it and/or modify it
  under the terms of the GNU Lesser General Public License as published
  by the Free Software Foundation; either version 2.1 of the License, or
  any later version.

  This library is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
  MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
  documentation provided hereunder is on an "as is" basis, and the
  Institute for Systems Biology and the Whitehead Institute
  have no obligations to provide maintenance, support,
  updates, enhancements or modifications.  In no event shall the
  Institute for Systems Biology and the Whitehead Institute
  be liable to any party for direct, indirect, special,
  incidental or consequential damages, including lost profits, arising
  out of the use of this software and its documentation, even if the
  Institute for Systems Biology and the Whitehead Institute
  have been advised of the possibility of such damage.  See
  the GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
*/

package edu.ucsf.rbvi.chemViz2.internal.ui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import java.net.URI;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.ucsf.rbvi.chemViz2.internal.model.HTMLObject;

public class HTMLEditor extends AbstractCellEditor implements TableCellEditor {
	private final JTextPane component;

	public HTMLEditor () {
		super();
		component = new JTextPane();
	}

	public Component getTableCellEditorComponent(JTable table, final Object value, boolean isSelected,
	                                             int row, int column) {
		// Paint border
		if (isSelected) {
			component.setBorder(BorderFactory.createEtchedBorder());
		} else {
			component.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		}
		component.setContentType("text/html");
		component.setText(value.toString());
		// component.addMouseListener(this);
		component.addHyperlinkListener(new MyHyperlinkListener());
		component.setEditable(false);
		component.setEnabled(true);
		return component;
	}

	public Object getCellEditorValue() {
		return component.getText();
	}

	class MyHyperlinkListener implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch(Exception ex) {
						System.err.println("Unable to launch browser");
					}
				} else {
					System.out.println("Desktop not supported");
				}
			}
		}
	}
}
