/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package edu.toronto.cs.phenotips.components;

import static org.mockito.Mockito.when;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Provider;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.component.util.DefaultParameterizedType;
import org.xwiki.observation.event.ApplicationStartedEvent;
import org.xwiki.observation.event.Event;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

/**
 * Tests for the {@link MeasurementsScriptService} component.
 * 
 * @version $Id$
 * @since 1.0M1
 */
public class ComponentManagerRegistryTest
{
    ParameterizedType cmType = new DefaultParameterizedType(null, Provider.class, ComponentManager.class);

    @Rule
    public final MockitoComponentMockingRule<ComponentManagerRegistry> mocker =
        new MockitoComponentMockingRule<ComponentManagerRegistry>(ComponentManagerRegistry.class);

    @SuppressWarnings("static-access")
    @Test
    public void testGetContextComponentManager() throws ComponentLookupException
    {
        Provider<ComponentManager> provider = this.mocker.getInstance(this.cmType, "context");
        ComponentManager cm = Mockito.mock(ComponentManager.class);
        when(provider.get()).thenReturn(cm);
        Assert.assertSame(cm, this.mocker.getComponentUnderTest().getContextComponentManager());
    }

    @Test
    public void testGetName() throws ComponentLookupException
    {
        Assert.assertEquals("cmregistry", this.mocker.getComponentUnderTest().getName());
    }

    @Test
    public void testGetEvents() throws ComponentLookupException
    {
        List<Event> events = this.mocker.getComponentUnderTest().getEvents();
        Assert.assertEquals(1, events.size());
        Assert.assertTrue(events.get(0).matches(new ApplicationStartedEvent()));
    }

    @Test
    public void testOnEventWithNullParameters() throws ComponentLookupException
    {
        // Just call the method and see that no NPE is thrown
        this.mocker.getComponentUnderTest().onEvent(null, null, null);
    }
}