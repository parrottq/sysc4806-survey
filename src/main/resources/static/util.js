/**
 * Generates a UUID in the format the backend expects
 * @returns {`${string}-${string}-${string}-${string}-${string}`}
 */
function generateUUID() {
    return this.crypto.randomUUID();
}