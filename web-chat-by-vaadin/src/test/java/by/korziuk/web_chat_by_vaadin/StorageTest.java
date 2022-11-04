package by.korziuk.web_chat_by_vaadin;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void get_messages_should_return_instance_concurrent_linked_queue() {
        assertInstanceOf(ConcurrentLinkedQueue.class, new Storage().getMessages());
    }

    @Test
    void add_record_should_add_two_records_to_queue() {
        Storage storage = new Storage();
        storage.addRecord("Username1", "Message1");
        storage.addRecord("Username2", "Message2");
        assertEquals(2, storage.getMessages().size());
    }

    @Test
    void add_record_joined_should_add_username_in_field_message() {
        Storage storage = new Storage();
        storage.addRecordJoined("UserName");
        assertEquals("", storage.getMessages().element().user, "must be empty");
        assertEquals("UserName", storage.getMessages().element().message, "must be equil username");
    }

    @Test
    void size_should_return_one() {
        Storage storage = new Storage();
        assertEquals(0, storage.size());
        storage.addRecord("Username1", "Message1");
        assertEquals(1, storage.size());
    }
}