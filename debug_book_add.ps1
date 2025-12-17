$baseUrl = "http://localhost:8080"

# 1. Create Author
$authorBody = @{
    name = "Debug Author"
    email = "debug@example.com"
} | ConvertTo-Json

try {
    Write-Host "Creating Author..."
    $author = Invoke-RestMethod -Uri "$baseUrl/authors" -Method Post -Body $authorBody -ContentType "application/json"
    Write-Host "Author Created: ID $($author.id)"
} catch {
    Write-Error "Failed to create Author: $_"
    # Continue anyway? No, book will fail.
}

# 2. Create Publisher
$publisherBody = @{
    name = "Debug Publisher"
    address = "Debug Address"
} | ConvertTo-Json

try {
    Write-Host "Creating Publisher..."
    $publisher = Invoke-RestMethod -Uri "$baseUrl/publishers" -Method Post -Body $publisherBody -ContentType "application/json"
    Write-Host "Publisher Created: ID $($publisher.id)"
} catch {
    Write-Error "Failed to create Publisher: $_"
}

if ($author -and $publisher) {
    # 3. Create Book
    $bookBody = @{
        title = "Debug Book"
        isbn = "999-9999999999"
        price = 10.0
        quantity = 5
        category = "Debug"
        authorId = $author.id
        publisherId = $publisher.id
    } | ConvertTo-Json

    Write-Host "Sending Book Request: $bookBody"

    try {
        Write-Host "Creating Book..."
        $book = Invoke-RestMethod -Uri "$baseUrl/books" -Method Post -Body $bookBody -ContentType "application/json"
        Write-Host "Book Created Successfully! ID: $($book.id)"
    } catch {
        Write-Error "Failed to create Book."
        if ($_.Exception.Response) {
             Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)"
             $stream = $_.Exception.Response.GetResponseStream()
             if ($stream) {
                 $reader = New-Object System.IO.StreamReader($stream)
                 $responseBody = $reader.ReadToEnd()
                 Write-Host "Server Response: $responseBody" -ForegroundColor Red
             }
        }
    }
} else {
    Write-Warning "Skipping Book creation because Author or Publisher failed."
}
